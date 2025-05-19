package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Notification;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class NotificationRepository {

    private final DataSource dataSource;

    public NotificationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Notification> getRepairNotifications(){
        ArrayList<Notification> listOfNotifications = new ArrayList<>();

        listOfNotifications.addAll(getGlobalNotifications());
        listOfNotifications.addAll(getCarNeedsRepairNotifications());

        return listOfNotifications;
    }

    ArrayList<Notification> getGlobalNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();

        listToReturn.add(new Notification("positive", "Husk der er sommerfest d. 20/6! Kom glad <3"));
        listToReturn.add(new Notification("negative", "fervent store fyringsrunder i morgen"));

        return listToReturn;
    }

    ArrayList<Notification> getCarNeedsRepairNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();
        //First, look through the database to find the licenseplates for cars, that have pending damages
        //Join the damages table to the car table on LeaseID

        String sql = "SELECT DISTINCT cars.chassis_no, leases.lease_id FROM damages JOIN leases ON damages.lease_id = leases.lease_id JOIN cars ON leases.vehicle_no = cars.vehicle_no WHERE isFixed = false;";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet res = preparedStatement.executeQuery();

            while(res.next()){
                Notification notification = new Notification("negative", "Bil med stelnummer: " + res.getString("chassis_no") + " har skader som ikke er udbedrede endnu.\n Lease ID = "+res.getString("lease_id"));
                listToReturn.add(notification);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return listToReturn;
    }

    ArrayList<Notification> getLeaseNeedsPaymentNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();
        String sql = "SELECT DISTINCT cars.chassis_no, leases.lease_id FROM damages JOIN leases ON damages.lease_id = leases.lease_id JOIN cars ON leases.vehicle_no = cars.vehicle_no WHERE isFixed = true AND isPaid = false OR isPaid IS NULL;\n";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet res = preparedStatement.executeQuery();

            while(res.next()){
                Notification notification = new Notification("negative", "Bil med stelnummer: " + res.getString("chassis_no") + " har skader som ikke er fikset men ikke betalt.  Lease ID = "+res.getString("lease_id"));
                listToReturn.add(notification);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return listToReturn;
    }

    public ArrayList<Notification> getDataRegNotifications(){
        ArrayList<Notification> listOfNotifications = new ArrayList<>();

        listOfNotifications.addAll(getGlobalNotifications());
        listOfNotifications.addAll(getLeaseNeedsPaymentNotifications());

        return listOfNotifications;
    }

}
