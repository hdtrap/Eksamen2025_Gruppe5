package org.example.eksamen2025_gruppe5.model;


import java.sql.ResultSet;
import java.sql.SQLException;

enum Role {DATA, REPAIR, BUSINESS, SYSADMIN}

public class User {
    String firstName;
    String lastName;
    String userName;
    String Password;
    Role role;

    public User(String firstName, String lastName, String userName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        Password = password;
        this.role = Role.valueOf(role.toUpperCase());
    }

    public User(ResultSet res) throws SQLException {
        this.firstName = res.getString("first_name");
        this.lastName = res.getString("last_name");
        this.userName = res.getString("username");
        Password = res.getString("password");
        this.role = Role.valueOf(res.getString("role"));
    }

    public User(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleAsString(){
        return role.toString();
    }

    public Boolean isAdmin(){
        if(this.role == role.SYSADMIN){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isDataReg(){
        if(this.role == role.DATA){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isRepair(){
        if(this.role == role.REPAIR){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isBusiness(){
        if(this.role == role.BUSINESS){
            return true;
        }
        else {
            return false;
        }
    }
}
