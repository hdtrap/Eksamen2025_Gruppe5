package org.example.eksamen2025_gruppe5.model;


import org.example.eksamen2025_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

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
        this.role = Role.valueOf(role);
    }
}
