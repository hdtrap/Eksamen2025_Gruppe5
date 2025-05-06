package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.model.TypeOfLease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class LeaseRepository {

@Autowired
    DataSource dataSource;
@Autowired
CarRepository carRepository;

    // Oprette en lejeaftale
    public void saveLease(Lease lease){
        // SQL forespørgsel
        String sqlRequest = "INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease, fully_processed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);){

            statement.setInt(1, lease.getCar().getVehicleNumber());
            statement.setDate(2, Date.valueOf(lease.getStartDate()));
            statement.setDate(3, Date.valueOf(lease.getEndDate()));
            statement.setString(4, lease.getCustomerName());
            statement.setString(5, lease.getCustomerEmail());
            statement.setString(6, lease.getCustomerNumber());
            statement.setDouble(7, lease.getPriceToStart());
            statement.setDouble(8, lease.getPricePrMonth());
            statement.setString(9, String.valueOf(lease.getTypeOfLease()));
            statement.setBoolean(10, lease.isFullyProcessed());

            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Metode til at finde biler ved deres lease ID
    public Lease findLeaseById(int leaseId){
        Lease lease = new Lease();
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
                lease.setTypeOfLease(TypeOfLease.valueOf("type_of_lease"));

                //Sætter bilen ind på lease ved hjælp carRepository.findCarByVehicleNumber
                int vehicleNumber = resultSet.getInt("vehicle_no");
                Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
                lease.setCar(car);

            }

            } catch (SQLException e) {
                e.printStackTrace();
        }

        return lease;
    }

    // Opdater en lejeaftale
    public void updateLease(Lease lease){
        String sqlRequest = "UPDATE leases SET vehicle_no = ?, start_date = ?, end_date = ?, customer_number = ?," +
                " customer_email = ?, customer_number = ?, price_to_start = ?, price_pr_month = ?, type_of_lease = ? WHERE lease_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest);){

            statement.setInt(1, lease.getCar().getVehicleNumber());
            statement.setDate(2, Date.valueOf(lease.getStartDate()));
            statement.setDate(3, Date.valueOf(lease.getEndDate()));
            statement.setString(4, lease.getCustomerName());
            statement.setString(5, lease.getCustomerEmail());
            statement.setString(6, lease.getCustomerNumber());
            statement.setDouble(7, lease.getPriceToStart());
            statement.setDouble(8, lease.getPricePrMonth());
            statement.setString(9, String.valueOf(lease.getTypeOfLease()));

            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
