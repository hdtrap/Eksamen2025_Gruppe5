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
                car.setStatusOfCar(StatusOfCar.valueOf(resultSet.getString("status_of_car")));

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
    public void makeCarStatusAvailable(Car car){
        String sql = "UPDATE cars SET status_of_car = ? WHERE vehicle_no = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println("der sker noget i fmakeCarAvailabe");
            statement.setString(1, "AvailableToLease");
            statement.setInt(2, car.getVehicleNumber() );
            System.out.println("Bilen er tilgængelig");
            statement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public void makeCarStatusGettingRepaired(Car car){
        String sql = "UPDATE cars SET status_of_car = ? WHERE vehicle_no = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "GettingRepaired");
            statement.setInt(2, car.getVehicleNumber() );
            System.out.println("Bilen er GettingRepaired");
            statement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public void makeCarStatusPendingEvaluation(Car car){
        String sql = "UPDATE cars SET status_of_car = ? WHERE vehicle_no = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "PendingEvaluation");
            statement.setInt(2, car.getVehicleNumber() );
            System.out.println("Bilen er PendingEvaluation");
            statement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public ArrayList<Car> getAllCars() {
        ArrayList<Car> allCars = new ArrayList<>();

        String sqlRequest = "SELECT * FROM cars";

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

                allCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCars;
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
        return leasedCars;
    }

    public ArrayList<Car> getNonLeasedCars() {
        ArrayList<Car> nonLeasedCars = new ArrayList<>();

        String sqlRequest = "SELECT * FROM cars JOIN leases ON cars.vehicle_no = leases.vehicle_no " +
                "WHERE leases.end_date < CURRENT_DATE";

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

                nonLeasedCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nonLeasedCars;
    }

    public CarModel mostCommonModel() {
        CarModel mostCommon = new CarModel();

        /*
        Select all attributes from car_models
        COUNT(*) AS most_common counts how many appear in each group - most_common is label for each group
        Join the three tables so all values are reachable
        WHERE clause so only active leases are counted
        GROUP BY car_models.id so the groups counted are divided by car_models.id
        ORDER BY DESC puts the biggest group on top
        LIMIT 1 only selects one row, which will be the top value, which is in the biggest group, thus the most common value
        */
        String sqlRequest = "SELECT car_models.*, COUNT(*) AS most_common " +
                "FROM cars JOIN leases ON cars.vehicle_no = leases.vehicle_no " +
                "JOIN car_models ON cars.car_model = car_models.id " +
                "WHERE leases.start_date <= CURRENT_DATE AND leases.end_date > CURRENT_DATE " +
                "GROUP BY car_models.id " +
                "ORDER BY most_common DESC LIMIT 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                mostCommon.setCarModelId(resultSet.getInt("id"));
                mostCommon.setBrand(resultSet.getString("brand"));
                mostCommon.setModel(resultSet.getString("model"));
                mostCommon.setProductionYear(resultSet.getInt("production_year"));
                mostCommon.setFuelType(resultSet.getString("fuel_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostCommon;
    }

}


