package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CarRepository {

    @Autowired
    DataSource dataSource;

    //Finder en bil i Databasen som kan sættes på Lease objekter
    public Car findCarByVehicleNumber(int vehicleNumber) {
        System.out.println("CarRepository.findCarByVehicleNumber");
        Car car = new Car();
        String sql = "SELECT * FROM cars WHERE vehicle_no = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicleNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car.setVehicleNumber(resultSet.getInt("vehicle_no"));
                car.setChassisNumber(resultSet.getString("chassis_no"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setProductionYear(resultSet.getInt("production_year"));
                car.setPrice(resultSet.getDouble("price"));
                //Mangler Fuel
                //Mangler Available
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }
}

