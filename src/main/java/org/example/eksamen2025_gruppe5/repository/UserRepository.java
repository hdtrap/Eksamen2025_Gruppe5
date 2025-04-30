package org.example.eksamen2025_gruppe5.repository;

import org.example.eksamen2025_gruppe5.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    void saveUser(User createdUser){
        System.out.println("AT this point the user will be saved");
        //Save user to SQL
    }
}
