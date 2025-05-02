package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class LeaseRepository {

@Autowired
    DataSource dataSource;



    // Oprette en lejeaftale
    public void createLease(Lease lease){
        // SQL forespørgsel
        String sqlRequest = "INSRT INTO ";
    }
}
