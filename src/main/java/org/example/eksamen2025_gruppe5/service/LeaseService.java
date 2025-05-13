package org.example.eksamen2025_gruppe5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Service
public class LeaseService {

    @Autowired
    DataSource dataSource;

    public int monthCalculator(LocalDate startDate, LocalDate endDate){
        return Period.between(startDate, endDate).getMonths();
    }

    public void addSelectedAddonsToLease(int leaseId, ArrayList<Integer> selectedAddons) {
        String sqlRequest = "INSERT INTO add_ons (addon_type, lease_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            connection.setAutoCommit(false); // starter transaction

            for (Integer addonTypeId : selectedAddons) {
                statement.setInt(1, addonTypeId);
                statement.setInt(2, leaseId);
                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
