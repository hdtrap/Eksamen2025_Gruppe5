package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.model.CarModel;
import org.example.eksamen2025_gruppe5.model.StatusOfCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CarRepository {

    @Autowired
    DataSource dataSource;

    @Autowired
    CarModelRepository carModelRepository;

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

                int carModelId = resultSet.getInt("car_model");
                CarModel carModel = carModelRepository.findCarModelFromId(carModelId);
                car.setCarModel(carModel);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }

    public ArrayList<Car> getAvailableCars() {
        ArrayList<Car> availableCars = new ArrayList<>();

        String sqlRequest = "SELECT * FROM cars WHERE status_of_car = 'AvailableToLease'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setVehicleNumber(resultSet.getInt("vehicle_no"));
                car.setChassisNumber(resultSet.getString("chassis_no"));
                car.setPrice(resultSet.getDouble("price"));

                int carModelId = resultSet.getInt("car_model");
                CarModel carModel = carModelRepository.findCarModelFromId(carModelId);
                car.setCarModel(carModel);

                availableCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableCars;
    }

    public int totalCars() {
        int totalCars = 0;

        String sql = "SELECT COUNT(*) AS total_cars FROM cars";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCars = resultSet.getInt("total_cars");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return totalCars;
    }

    public ArrayList<Car> getLeasedCars() {
        ArrayList<Car> leasedCars = new ArrayList<>();

        String sqlRequest = "SELECT * FROM cars JOIN leases ON cars.vehicle_no = leases.vehicle_no " +
                "WHERE leases.start_date <= CURRENT_DATE AND leases.end_date > CURRENT_DATE";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setVehicleNumber(resultSet.getInt("vehicle_no"));
                car.setChassisNumber(resultSet.getString("chassis_no"));
                int carModelId = resultSet.getInt("car_model");
                CarModel carModel = carModelRepository.findCarModelFromId(carModelId);
                car.setCarModel(carModel);
                car.setPrice(resultSet.getDouble("price"));
                car.setStatusOfCar(StatusOfCar.Leased);

                leasedCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(leasedCars);
        return leasedCars;
    }

}


