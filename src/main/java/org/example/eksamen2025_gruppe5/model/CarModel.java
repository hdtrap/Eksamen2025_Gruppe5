package org.example.eksamen2025_gruppe5.model;

public class CarModel {
    private int carModelId;
    private String brand;
    private String model;
    private int productionYear;
    private String fuelType;

    public CarModel(String brand, String model, int productionYear, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
    }

    public CarModel() {}

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
