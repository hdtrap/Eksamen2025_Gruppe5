package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Damage;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class DamageRepository {

    @Autowired
    DataSource dataSource;

    //Frederik
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

    //Frederik
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

    //Frederik
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

    //Frederik
    public ArrayList<Damage> getAllDamagesForALeaseWithLeaseId(int leaseId) {
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

    //Frederik
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

    //Frederik
    public void fixDamage(boolean isFixed, int damage_id) {
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

    //Frederik
    public void payAllDamagesOnALease(int leaseId) {
        System.out.println("payAllDamagesOnALease leaseId : " + leaseId);
        String sql = "UPDATE damages SET isPaid = CASE WHEN isPaid IS NULL THEN true ELSE NOT isPaid END WHERE lease_id = ?"; //NOT isPaid betyder at den sætter boolean værdien til det modsatte af hvad en i forvejen, CASE WHEN isPaid IS NULL THEN true, betyder at når isPaid er null bliver den til true
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, leaseId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Frederik
    public void printSkadesRapport(Lease lease) throws IOException {
        ArrayList<Damage> damages = getAllDamagesForALeaseWithLeaseId(lease.getLeaseId());
        //Pathen hedder skaderapport da det er i den yderste mappe
        String folderPath = "skaderapport";
        File folder = new File(folderPath);
        //Navnet på den fil der bliver lavet og dens path
        String filePath = folderPath + "/skaderapport_" + lease.getLeaseId() + ".txt";
        FileWriter fw = new FileWriter(filePath);
        double totalpris = 0;

        try (BufferedWriter writer = new BufferedWriter(fw)) {
            writer.write("Skaderapport for lease ID: " + lease.getLeaseId());
            writer.newLine();
            writer.write("Kunde: " + lease.getCustomerName());
            writer.newLine();
            writer.write("----------------------------------");
            writer.newLine();

            for (Damage damage : damages) {
                writer.newLine();
                writer.write("Skade id: " + damage.getDamageId());
                writer.newLine();
                writer.write("Skade type: " + damage.getDamageType());
                writer.newLine();
                writer.write("Kategori: " + damage.getCategory());
                writer.newLine();
                writer.write("Pris: " + damage.getPrice() + " kr.");
                writer.newLine();
                writer.write("----------------------------------");
                writer.newLine();
                totalpris += damage.getPrice();
            }
            writer.write("Totalpris til betaling: " + totalpris + "DKK ");
            writer.newLine();
            writer.newLine();
            writer.write("Har du spørgsmål eller brug for hjælp, så sidder vi klar til at hjælpe dig.");
            writer.newLine();
            writer.newLine();
            writer.write("Hverdage - 10.00-15.00");
            writer.newLine();
            writer.write("(Telefonen er lukket mellem 11:30 - 12:00)");
            writer.newLine();
            writer.newLine();
            writer.write("+45 89 88 50 80");
            writer.newLine();
            writer.newLine();
            writer.write("support@bilabonnement.dk");
            writer.newLine();
            writer.newLine();
            writer.write("Du finder os på Vibeholmsvej 31, 2605 Brøndby,");
            writer.newLine();
            writer.write("hvor vi både har vores administration og værksted til klargøring af biler.");
            writer.newLine();

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
