package org.example.eksamen2025_gruppe5.model;


//Sarah
public class Notification {
    String type;
    String message;

    public Notification(String type, String message){
        this.type = type;
        this.message=message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
