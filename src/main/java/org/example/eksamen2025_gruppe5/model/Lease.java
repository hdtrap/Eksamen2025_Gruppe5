package org.example.eksamen2025_gruppe5.model;

import java.time.LocalDate;

public class Lease {
    private int leaseId;
    private boolean isActive;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private int leaseTimeInMonths;

    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLeaseTimeInMonths(int leaseTimeInMonths) {
        this.leaseTimeInMonths = leaseTimeInMonths;
    }

    public int getLeaseId() {
        return leaseId;
    }

    public boolean isActive() {
        return isActive;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getLeaseTimeInMonths() {
        return leaseTimeInMonths;
    }
}
