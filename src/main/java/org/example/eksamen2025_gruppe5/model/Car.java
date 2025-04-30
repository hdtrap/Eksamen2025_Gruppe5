package org.example.eksamen2025_gruppe5.model;
enum Fuel {diesel,benzin,electric,hybrid}
public class Car {

    private int vehicleNumber;
    private String chassisNumber;
    private String brand;
    private String model;
    private int productionYear;
    private double price;
    private Fuel fuel;

    public String getBrand() {
        return brand;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }
}

