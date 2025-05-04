package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class LeaseRepository {

@Autowired
    DataSource dataSource;



    // Oprette en lejeaftale
    public void saveLease(Lease lease){
        // SQL forespørgsel
        String sqlRequest = "INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
