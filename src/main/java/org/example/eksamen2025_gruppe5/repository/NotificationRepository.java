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

        listOfNotifications.addAll(getCarNeedsRepairNotifications());

        return listOfNotifications;
    }

    ArrayList<Notification> getCarNeedsRepairNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();
        //First, look through the database to find the licenseplates for cars, that have pending damages
        //Join the damages table to the car table on LeaseID

        String sql = "SELECT DISTINCT cars.chassis_no, leases.lease_id FROM damages JOIN leases ON damages.lease_id = leases.lease_id JOIN cars ON leases.vehicle_no = cars.vehicle_no;";

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
}
