package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    DataSource dataSource;

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
}
