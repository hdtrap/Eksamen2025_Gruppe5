package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.CarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Sarah
@Repository
public class CarModelRepository {

    @Autowired
    DataSource dataSource;

    public CarModel findCarModelFromId(int carModelId){
        CarModel carModel = new CarModel();
        String sqlRequest = "SELECT * FROM car_models WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setInt(1, carModelId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                carModel.setBrand(resultSet.getString("brand"));
                carModel.setModel(resultSet.getString("model"));
                carModel.setProductionYear(resultSet.getInt("production_year"));
                carModel.setFuelType(resultSet.getString("fuel_type"));
                carModel.setColor(resultSet.getString("color"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carModel;
    }
}
