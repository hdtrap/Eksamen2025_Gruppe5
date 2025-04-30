package org.example.eksamen2025_gruppe5.repository;

import jakarta.servlet.http.HttpSession;
import org.example.eksamen2025_gruppe5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class UserRepository {

    @Autowired
    DataSource dataSource;

    HashMap<String, User> loggedInUsers = new HashMap<>();

    @Autowired
    HttpSession httpSession;

    public void saveUser(User createdUser){
        System.out.println("AT this point the user will be saved");

        String sql = "INSERT INTO Users (username, first_name, last_name, password, role) VALUES (?,?,?,?,?);";

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
        }
    }

    public Boolean LogInUserSuccess(String username, String passWord){
        String sql = "SELECT * FROM Users WHERE username=? AND password=?;";

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

    public String findNumberForUsername(String firstLetters){

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
            System.out.println("Whoops");
            e.printStackTrace();
        }


        return 0+"";
    }
}
