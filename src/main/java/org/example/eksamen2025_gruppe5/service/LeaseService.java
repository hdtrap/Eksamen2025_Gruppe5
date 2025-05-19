package org.example.eksamen2025_gruppe5.service;

import org.example.eksamen2025_gruppe5.model.AddOnType;
import org.example.eksamen2025_gruppe5.model.CarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Service
public class LeaseService {

    @Autowired
    DataSource dataSource;

    public int monthCalculator(LocalDate startDate, LocalDate endDate) {
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

    public ArrayList<AddOnType> showSelectedAddons(int leaseId) {
        String sqlRequest = "SELECT add_ons.id, addon_types.type, addon_types.price FROM add_ons JOIN addon_types ON add_ons.addon_type = addon_types.id WHERE add_ons.lease_id = ?";
        ArrayList<AddOnType> selectedAddons = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setInt(1, leaseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AddOnType addOnType = new AddOnType();
                addOnType.setAddOnTypeId(resultSet.getInt("id"));
                addOnType.setType(resultSet.getString("type"));
                addOnType.setPrice(resultSet.getDouble("price"));
                selectedAddons.add(addOnType);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedAddons;
    }

    public void updateSelectedAddonsOnLease(int leaseId, ArrayList<Integer> selectedAddons) {
        String sqlDelete = "DELETE FROM add_ons WHERE lease_id = ?";
        String sqlInsert = "INSERT INTO add_ons (addon_type, lease_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false); // starter transaction

        try (PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete);
             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {

            deleteStatement.setInt(1, leaseId);
            deleteStatement.executeUpdate();

            for (Integer addonTypeId : selectedAddons) {
                insertStatement.setInt(1, addonTypeId);
                insertStatement.setInt(2, leaseId);
                insertStatement.addBatch();
            }

            insertStatement.executeBatch();
            connection.commit();

        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
