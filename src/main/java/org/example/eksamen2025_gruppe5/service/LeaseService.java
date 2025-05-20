package org.example.eksamen2025_gruppe5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.Period;

@Service
public class LeaseService {

    @Autowired
    DataSource dataSource;

    public int monthCalculator(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).getMonths();
    }

}
