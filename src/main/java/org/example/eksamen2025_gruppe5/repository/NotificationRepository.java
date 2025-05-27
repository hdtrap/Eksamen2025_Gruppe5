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

    public ArrayList<Notification> getDataRegNotifications(){
        System.out.println("Henter DataReg Notifications");

        ArrayList<Notification> listOfNotifications = new ArrayList<>();

        listOfNotifications.addAll(getCarIsNotDeliveredNotification());
        listOfNotifications.addAll(getGlobalNotifications());

        return listOfNotifications;
    }

    public ArrayList<Notification> getRepairNotifications(){
        ArrayList<Notification> listOfNotifications = new ArrayList<>();

        System.out.println("Henter repair Notifications");

        listOfNotifications.addAll(getGlobalNotifications());
        listOfNotifications.addAll(getCarNeedsRepairNotifications());

        return listOfNotifications;
    }

    public ArrayList<Notification> getBusinessNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();
        System.out.println("Henter Business Notifications");

        listToReturn.addAll(getGlobalNotifications());

        return listToReturn;
    }

    public ArrayList<Notification> getAdminNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();
        System.out.println("Henter Admin Notifications");

        listToReturn.addAll(getGlobalNotifications());

        return listToReturn;
    }

    ArrayList<Notification> getCarIsNotDeliveredNotification(){
        ArrayList<Notification> listToReturn = new ArrayList<>();

        String sqlRequest = "SELECT cars.chassis_no, leases.lease_id, leases.end_date FROM leases JOIN cars ON cars.vehicle_no = leases.vehicle_no WHERE CURRENT_DATE > leases.end_date AND cars.status_of_car = 'Leased'";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Notification notification = new Notification("negative", "Lease ID = "+resultSet.getString("lease_id")+" udløb d. "+resultSet.getDate("end_date")+". Bil med stelnummer: " + resultSet.getString("chassis_no") + " er stadig ikke blevet afleveret.");
                listToReturn.add(notification);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return listToReturn;
    }


    ArrayList<Notification> getGlobalNotifications(){
        ArrayList<Notification> listToReturn = new ArrayList<>();

        listToReturn.add(new Notification("positive", "Husk der er sommerfest d. 20/6! Kom glad <3"));
        listToReturn.add(new Notification("negative", "Der udføres rutine vedligeholdelse af elevatoren i bygning A d. 4 juni. Brug trappen i stedet."));

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




}
