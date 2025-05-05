package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Damage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DamageRepository {

    @Autowired
    DataSource dataSource;

    public void saveDamage(Damage damage) {
        String sql = "INSERT INTO damages (damage_type, category, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) { //

            statement.setString(1, damage.getDamageType());
            statement.setInt(2, damage.getCategory());
            statement.setDouble(3, damage.getPrice());

            statement.executeUpdate(); // eksekverer dit statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteDamage(Damage damage) {
        String sql = "DELETE FROM damages WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, damage.getDamageId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateDamage(Damage damage) {
        String sql = "UPDATE damages WHERE damage_id = ? SET damage_type = ? Set category = ? Set price = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, damage.getDamageId());
            statement.setString(2, damage.getDamageType());
            statement.setInt(3, damage.getCategory());
            statement.setDouble(4, damage.getPrice());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }






}
