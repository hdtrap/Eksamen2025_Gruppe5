package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Damage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class DamageRepository {

    @Autowired
    DataSource dataSource;

    public void saveDamage(Damage damage) {
        System.out.println("Vi prøver save Damage");
        System.out.println("lease id i saveDamage = " + damage.getLeaseId());
        String sql = "INSERT INTO damages (lease_id, damage_type, category, price) VALUES (?, ?, ?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) { //

            statement.setInt(1, damage.getLeaseId());
            statement.setString(2, damage.getDamageType());
            statement.setInt(3, damage.getCategory());
            statement.setDouble(4, damage.getPrice());

            statement.executeUpdate(); // eksekverer dit statement
            System.out.println("Damage gemt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDamageWithDamageId(int damageId) {
        String sql = "DELETE FROM damages WHERE damage_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, damageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateDamage(Damage damage) {
        String sql = "UPDATE damages SET damage_type = ?, category = ?, price = ? WHERE damage_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println("der sker noget i update damage");
            System.out.println("damageid = " + damage.getDamageId());
            statement.setInt(4, damage.getDamageId());
            System.out.println(damage.getDamageType());
            statement.setString(1, damage.getDamageType());
            statement.setInt(2, damage.getCategory());
            statement.setDouble(3, damage.getPrice());
            System.out.println("update damage burde have været kørt");
            statement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public ArrayList<Damage> getAllDamagesForALeaseWithLeaseId(int leaseId){
        ArrayList<Damage> damages = new ArrayList<>();
        String sql = "SELECT * FROM damages WHERE lease_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, leaseId);
            // Jeg gemmer alt hvad jeg har hentet i SQL i mit result set
            ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int damageId = resultSet.getInt("damage_id");
            String damageType = resultSet.getString("damage_type");
            int category = resultSet.getInt("category");
            double price = resultSet.getDouble("price");
            boolean isPaid = resultSet.getBoolean("isPaid");
            boolean isFixed = resultSet.getBoolean("isFixed");

            Damage damage = new Damage(damageId, leaseId, damageType, category, price, isPaid, isFixed);
            damages.add(damage);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return damages;
    }
    public Damage getDamageWithDamageId(int damageId) {
        String sql = "SELECT * FROM damages WHERE damage_id = ?";

        Damage damage = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, damageId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int leaseId = resultSet.getInt("lease_id");
                String damageType = resultSet.getString("damage_type");
                int category = resultSet.getInt("category");
                double price = resultSet.getDouble("price");

                damage = new Damage(damageId, leaseId, damageType, category, price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return damage;
    }
    public void fixDamage(boolean isFixed, int damage_id){
        String sql = "UPDATE damages SET isFixed = ? WHERE damage_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println("der sker noget i fix damage");
            statement.setBoolean(1, isFixed);
            statement.setInt(2, damage_id);
            System.out.println("damaged fixed");
            statement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }




}
