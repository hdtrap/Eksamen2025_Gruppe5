package org.example.eksamen2025_gruppe5.repository;

import jakarta.servlet.http.HttpSession;
import org.example.eksamen2025_gruppe5.exceptions.PassWordMismatchException;
import org.example.eksamen2025_gruppe5.exceptions.UserNameTakenException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotFoundException;
import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    HashMap<String, User> loggedInUsers = new HashMap<>();

    @Autowired
    HttpSession httpSession;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUser(User createdUser) throws SQLException{
        System.out.println("AT this point the user will be saved");

        String sql = "INSERT INTO users (username, first_name, last_name, password, role) VALUES (?,?,?,?,?);";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, createdUser.getUserName());
            preparedStatement.setString(2,createdUser.getFirstName());
            preparedStatement.setString(3, createdUser.getLastName());
            preparedStatement.setString(4, createdUser.getPassword());
            preparedStatement.setString(5, createdUser.getRoleAsString());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public Boolean LogInUserSuccess(String username, String passWord){
        String sql = "SELECT * FROM users WHERE username=? AND password=?;";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passWord);

            ResultSet res = preparedStatement.executeQuery();

            if (res.next()){
                //A user was found with those credentials
                System.out.println("User with this info was found");
                User loggedInUser = new User(res);
                loggedInUsers.put(httpSession.getId(), loggedInUser);
                System.out.println("User with the username " + loggedInUser.getUserName());
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public User getcurrentUser(){
        return loggedInUsers.get(httpSession.getId());
    }

    public String findNumberForUsername2(String firstLetters){

        String sql = "SELECT COUNT(*) FROM users WHERE username LIKE ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, firstLetters + "%");

            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                int amountOfUsers = res.getInt(1);

                String lastnumbers = String.format("%04d", amountOfUsers+1);
                return lastnumbers;
            }
            else{
                return "0001";
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return 0+"";
    }

    public String findNumberForUsername(String firstLetters){
        String sql = "SELECT * FROM users WHERE username LIKE ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, firstLetters + "____");

            //Add all users with those first letters to a resultset
            ResultSet res = preparedStatement.executeQuery();

            //Create a list to hold all the usernames
            ArrayList<String> userNamesFound = new ArrayList<>();

            //Loop through the users with the relevant names and add them to a list.
            while(res.next()){
                userNamesFound.add(res.getString("username"));
                System.out.println("Found: " + res.getString("username"));
            }

            //Find a way to isolate the user with the highest number attatched to their name
            ArrayList<Integer> listOfNumbers = new ArrayList<>();
            //For each user
            for (String username:userNamesFound){
                System.out.println("Username: " + username);
                //Check if the username is of such a type, that the last four symbols are numbers
                if (isUsernameUserXXXX(username)){
                    //Then, seperate those numbers and find the highest number
                    listOfNumbers.add(Integer.parseInt(username.substring(4,8)));
                    System.out.println("Tal fundet: " + username.substring(4,8));
                }
            }

            Integer highestNumber = 0000;
            for (Integer i:listOfNumbers){
                System.out.println("Number to check: " + i);
                if(i > highestNumber){
                    highestNumber = i;
                    System.out.println("Inde i IF: " + highestNumber);
                }
            }

            highestNumber++;
            System.out.println(highestNumber);

            String lastnumbers = String.format("%04d", highestNumber);
            System.out.println(lastnumbers);

            return lastnumbers;
        }
        catch (SQLException e){
            e.printStackTrace();
            return "ERROR";
        }
    }



    public boolean isUsernameUserXXXX(String username){
        System.out.println("Username follows format? Answer: " + username.matches("[a-zA-Z]{4}[0-9]{4}"));
        return username.matches("[a-zA-Z]{4}[0-9]{4}");
    }

    public User findUserByUserName(String username) throws UserNotFoundException{
        String sql = "SELECT * FROM users WHERE username=?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                return new User(rs);
            }
            else {
                throw new UserNotFoundException();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public void deleteUserByUserName(String username) throws UserNotFoundException {
        String sql = "DELETE FROM users WHERE username=?";

        //First, see if the username is in the databse, if it is, carry on
        if (findUserByUserName(username) != null){
            try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                //Set the username in the SQL
                preparedStatement.setString(1, username);

                //Perform the update, deleting the user
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else {
            //if the username is not registered in the database, throw an exception to be handled in the controller
            throw new UserNotFoundException();
        }


    }

    public void updateUser(String oldUserName, String newUserName, String newFirstName, String newLastname, String newPassWord, String newRole) throws UserNotFoundException, UserNameTakenException {

        //First, validate that the User has not been deleted already
        try{
            findUserByUserName(oldUserName);
            System.out.println("User has not already been deleted");
        }
        catch (UserNotFoundException e){
            System.out.println("User was not found in the system");
            throw new UserNotFoundException();
        }

        //Then, check if the new username is available
        if(isUserNameAvailable(newUserName)){
            System.out.println("Username is available");
            String sql = "UPDATE users SET username=?, first_name=?, last_name=?, password=?, role=? WHERE username=?";

            try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                //Declare the new values
                preparedStatement.setString(1, newUserName);
                preparedStatement.setString(2, newFirstName);
                preparedStatement.setString(3, newLastname);
                preparedStatement.setString(4, newPassWord);
                preparedStatement.setString(5, newRole);

                //Define the location (the old username)
                preparedStatement.setString(6, oldUserName);

                System.out.println("Statement reads: " + preparedStatement);

                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else {
            throw new UserNameTakenException();
        }
    }

    public Boolean isUserNameAvailable(String username){
        try{
            findUserByUserName(username);
            return false;
        }
        catch (UserNotFoundException e){
            return true;
        }
    }

    public void checkPassWordMatch(String passWord, String passWordControl) throws PassWordMismatchException{
        if(!passWord.equals(passWordControl)){
            throw new PassWordMismatchException();
        }
    }
}
