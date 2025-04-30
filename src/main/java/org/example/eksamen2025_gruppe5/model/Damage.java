package org.example.eksamen2025_gruppe5.model;

public class Damage {


    private int damageId;
    private int leaseId;
    private String damageType;
    private double price;
    private int category;


    public int getDamageId() {
        return damageId;
    }

    public int getLeaseId() {
        return leaseId;
    }

    public String getDamageType() {
        return damageType;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory() {
        return category;
    }

    public void setDamageId(int damageId) {
        this.damageId = damageId;
    }

    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
