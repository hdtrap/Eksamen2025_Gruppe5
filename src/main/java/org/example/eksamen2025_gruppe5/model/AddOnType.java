package org.example.eksamen2025_gruppe5.model;

public class AddOnType {
    private int AddOnTypeId;
    private String type;
    private String description;
    private double price;

    public AddOnType() {}

    public int getAddOnTypeId() {
        return AddOnTypeId;
    }

    public void setAddOnTypeId(int addOnTypeId) {
        AddOnTypeId = addOnTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
