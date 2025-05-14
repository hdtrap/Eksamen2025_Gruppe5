package org.example.eksamen2025_gruppe5.model;

public class Car {
    private int vehicleNumber;
    private String chassisNumber;
    private double price;
    private CarModel carModel;
    private StatusOfCar statusOfCar;


    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public StatusOfCar getStatusOfCar() {
        return statusOfCar;
    }

    public void setStatusOfCar(StatusOfCar statusOfCar) {
        this.statusOfCar = statusOfCar;
    }
}

