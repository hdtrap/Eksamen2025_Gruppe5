package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.AddOnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class AddOnTypeRepository {

    @Autowired
    DataSource dataSource;

    public ArrayList<AddOnType> getAllAddOnTypes() {
        ArrayList<AddOnType> addOnTypes = new ArrayList<>();
        String sqlRequest = "SELECT * FROM addon_types";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                AddOnType addOnType = new AddOnType();
                addOnType.setAddOnTypeId(resultSet.getInt("id"));
                addOnType.setType(resultSet.getString("type"));
                addOnType.setDescription(resultSet.getString("description"));
                addOnType.setPrice(resultSet.getDouble("price"));
                addOnTypes.add(addOnType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addOnTypes;
    }
}
