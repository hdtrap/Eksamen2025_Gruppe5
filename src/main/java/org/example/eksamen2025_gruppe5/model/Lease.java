package org.example.eksamen2025_gruppe5.model;

import java.time.LocalDate;

public class Lease {
    private int leaseId;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private int leaseTimeInMonths;
    private String customerName;
    private String customerEmail;
    private String customerNumber;
    private double priceToStart;
    private double pricePrMonth;
    private String typeOfLease;
    private boolean fullyProcessed;

    public Lease(int leaseId, Car car, LocalDate startDate, LocalDate endDate,
                 int leaseTimeInMonths, String customerName, String customerEmail,
                 String customerNumber, double priceToStart, double pricePrMonth,
                 boolean fullyProcessed, String typeOfLease) {
        this.leaseId = leaseId;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaseTimeInMonths = leaseTimeInMonths;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerNumber = customerNumber;
        this.priceToStart = priceToStart;
        this.pricePrMonth = pricePrMonth;
        this.fullyProcessed = fullyProcessed;
        this.typeOfLease = typeOfLease;
    }

    public Lease(){}

    public int getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getLeaseTimeInMonths() {
        return leaseTimeInMonths;
    }

    public void setLeaseTimeInMonths(int leaseTimeInMonths) {
        this.leaseTimeInMonths = leaseTimeInMonths;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public double getPriceToStart() {
        return priceToStart;
    }

    public void setPriceToStart(double priceToStart) {
        this.priceToStart = priceToStart;
    }

    public double getPricePrMonth() {
        return pricePrMonth;
    }

    public void setPricePrMonth(double pricePrMonth) {
        this.pricePrMonth = pricePrMonth;
    }

    public boolean isFullyProcessed() {
        return fullyProcessed;
    }

    public void setFullyProcessed(boolean fullyProcessed) {
        this.fullyProcessed = fullyProcessed;
    }

    public String getTypeOfLease() {
        return typeOfLease;
    }

    public void setTypeOfLease(String typeOfLease) {
        this.typeOfLease = typeOfLease;
    }
}
