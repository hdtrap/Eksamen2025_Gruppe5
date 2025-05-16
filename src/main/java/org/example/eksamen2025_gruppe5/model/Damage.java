package org.example.eksamen2025_gruppe5.model;

public class Damage {


    private int damageId;
    private int leaseId;
    private String damageType;
    private double price;
    private int category;
    private boolean isPaid;
    private boolean isFixed;

    public Damage(int leaseId, String damageType, int category, double price) {
        this.leaseId = leaseId;
        this.damageType = damageType;
        this.price = price;
        this.category = category;
    }
    public Damage(int damageId,int leaseId, String damageType, int category, double price) {
        this.damageId = damageId;
        this.leaseId = leaseId;
        this.damageType = damageType;
        this.price = price;
        this.category = category;
    }

    public Damage(int damageId,int leaseId, String damageType, int category, double price, boolean isPaid, boolean isFixed) {
        this.damageId = damageId;
        this.leaseId = leaseId;
        this.damageType = damageType;
        this.price = price;
        this.category = category;
        this.isPaid = isPaid;
        this.isFixed = isFixed;
    }
    public boolean isPaid() {
        return isPaid;
    }
    public boolean isFixed() {
        return isFixed;
    }

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
    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }
    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
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
