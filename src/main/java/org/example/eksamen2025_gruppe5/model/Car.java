package org.example.eksamen2025_gruppe5.model;
enum Fuel {diesel,benzin,electric,hybrid}
public class Car {

    private int vehicleNumber;
    private String chassisNumber;
    private String model;
    private double price;
    private Fuel fuel;

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

