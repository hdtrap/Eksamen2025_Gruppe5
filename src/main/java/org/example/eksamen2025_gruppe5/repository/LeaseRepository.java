package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.model.TypeOfLease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class LeaseRepository {

@Autowired
    DataSource dataSource;

@Autowired
CarRepository carRepository;

    // Oprette en lejeaftale
    public int saveLease(Lease lease){
        // SQL forespørgsel
        String sqlLeaseRequest = "INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease, fully_processed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCarRequest = "UPDATE cars SET status_of_car = 'Leased' WHERE vehicle_no = ?";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement leaseStatement = connection.prepareStatement(sqlLeaseRequest, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement carStatement = connection.prepareStatement(sqlCarRequest)) {

                leaseStatement.setInt(1, lease.getCar().getVehicleNumber());
                leaseStatement.setDate(2, Date.valueOf(lease.getStartDate()));
                leaseStatement.setDate(3, Date.valueOf(lease.getEndDate()));
                leaseStatement.setString(4, lease.getCustomerName());
                leaseStatement.setString(5, lease.getCustomerEmail());
                leaseStatement.setString(6, lease.getCustomerNumber());
                leaseStatement.setDouble(7, lease.getPriceToStart());
                leaseStatement.setDouble(8, lease.getPricePrMonth());
                leaseStatement.setString(9, String.valueOf(lease.getTypeOfLease()));
                leaseStatement.setBoolean(10, lease.isFullyProcessed());
                leaseStatement.executeUpdate();

                carStatement.setInt(1, lease.getCar().getVehicleNumber());
                carStatement.executeUpdate();

                connection.commit();

                try (ResultSet generatedId = leaseStatement.getGeneratedKeys()) {
                    if (generatedId.next()) {
                        int leaseId = generatedId.getInt(1);
                        lease.setLeaseId(leaseId);
                        return leaseId;
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    // Metode til at finde biler ved deres lease ID
    public Lease findLeaseById(int leaseId) throws LeaseNotFoundException{
        Lease lease = new Lease(leaseId, null, null, null, null, null, null, null, null);
        String sql = "SELECT * FROM leases WHERE lease_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, leaseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lease.setLeaseId(resultSet.getInt("lease_id"));
                lease.setStartDate(resultSet.getDate("start_date").toLocalDate());
                lease.setEndDate(resultSet.getDate("end_date").toLocalDate());
                lease.setCustomerName(resultSet.getString("customer_name"));
                lease.setCustomerEmail(resultSet.getString("customer_email"));
                lease.setCustomerNumber(resultSet.getString("customer_number"));
                lease.setPriceToStart(resultSet.getDouble("price_to_start"));
                lease.setPricePrMonth(resultSet.getDouble("price_pr_month"));
                lease.setTypeOfLease(TypeOfLease.valueOf(resultSet.getString("type_of_lease")));

                //Sætter bilen ind på lease ved hjælp carRepository.findCarByVehicleNumber
                int vehicleNumber = resultSet.getInt("vehicle_no");
                Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
                lease.setCar(car);

            } else throw new LeaseNotFoundException();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new LeaseNotFoundException();
        }

        return lease;
    }

    // Opdater en lejeaftale
    public void updateLease(Lease lease){
        String sqlRequest = "UPDATE leases SET vehicle_no = ?, start_date = ?, end_date = ?, customer_name = ?," +
                " customer_email = ?, customer_number = ?, price_to_start = ?, price_pr_month = ?, type_of_lease = ? WHERE lease_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)){

            statement.setInt(1, lease.getCar().getVehicleNumber());
            statement.setDate(2, Date.valueOf(lease.getStartDate()));
            statement.setDate(3, Date.valueOf(lease.getEndDate()));
            statement.setString(4, lease.getCustomerName());
            statement.setString(5, lease.getCustomerEmail());
            statement.setString(6, lease.getCustomerNumber());
            statement.setDouble(7, lease.getPriceToStart());
            statement.setDouble(8, lease.getPricePrMonth());
            statement.setString(9, String.valueOf(lease.getTypeOfLease()));
            statement.setInt(10, lease.getLeaseId());

            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Slet en lejeaftale
    public void deleteLease(int id){
        String sql = "DELETE FROM leases WHERE lease_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Få månedlig indtjening
    public double monthlyRevenueFromActiveLeases() {
        double revenue = 0;
        String sql = "SELECT SUM(price_pr_month) AS total_expected_income FROM leases WHERE start_date <= CURRENT_DATE AND end_date > CURRENT_DATE";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    revenue = resultSet.getDouble("total_expected_income");
                }
             } catch (SQLException e) {
                e.printStackTrace();
        }
        return revenue;
    }

    // Få samlet værdig af alle udlejede biler

    // Find Id på biler som er lejet ud
    // Find samlet pris på de biler
    public double priceOfLeasedCars() {
        double price = 0;

        // Could say cars.price and not make references for the tables (not say leases l and cars c)
        String sql = "SELECT SUM(c.price) AS vehicle_price FROM leases l JOIN cars c ON l.vehicle_no = c.vehicle_no " +
                "WHERE start_date <= CURRENT_DATE AND end_date > CURRENT_DATE";

        // Connect to datasource
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    price = resultSet.getDouble("vehicle_price");
                }

             } catch (SQLException e) {
                e.printStackTrace();
        }
        return price;
    }

    // Få antal biler som bliver leaset ud
    public int noOfLeasedCars() {
        int noOfCars = 0;
        String sql = "SELECT COUNT(*) AS no_of_cars FROM leases WHERE start_date <= CURRENT_DATE and end_date > CURRENT_DATE";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                noOfCars = resultSet.getInt("no_of_cars");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(noOfCars);
        return noOfCars;
    }

    //
    public double avgDamageCost() {
        double avgDamage = 0;

        // Gets sum of damage.price divided by number of leases
        String sql = "SELECT SUM(d.price) / COUNT(l.lease_id) AS avg_damage_cost " +
                "FROM leases l LEFT JOIN damages d ON l.lease_id = d.lease_id WHERE l.fully_processed = 1";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                avgDamage = resultSet.getInt("avg_damage_cost");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return avgDamage;
    }

    public ArrayList<Lease> getAllLeases(){
        ArrayList<Lease> listToReturn = new ArrayList<>();
        String sql = "SELECT * FROM leases";

        //Get all leases from SQL statement
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Car car = carRepository.findCarByVehicleNumber(resultSet.getInt("vehicle_no"));
                Lease leaseToAdd = new Lease(
                        resultSet.getInt("lease_id"),
                        car,
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_email"),
                        resultSet.getString("customer_number"),
                        resultSet.getDouble("price_to_start"),
                        resultSet.getDouble("price_pr_month"),
                        resultSet.getString("type_of_lease"));

                listToReturn.add(leaseToAdd);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listToReturn;
    }

    public ArrayList<Lease> getAllActiveLeases(){
        ArrayList<Lease> listToReturn = new ArrayList<>();
        String sql = "SELECT * FROM leases WHERE start_date <= CURRENT_DATE and end_date > CURRENT_DATE";

        //Get all active leases from SQL statement
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Car car = carRepository.findCarByVehicleNumber(resultSet.getInt("vehicle_no"));
                Lease leaseToAdd = new Lease(
                        resultSet.getInt("lease_id"),
                        car,
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_email"),
                        resultSet.getString("customer_number"),
                        resultSet.getDouble("price_to_start"),
                        resultSet.getDouble("price_pr_month"),
                        resultSet.getString("type_of_lease"));

                listToReturn.add(leaseToAdd);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listToReturn;
    }
}