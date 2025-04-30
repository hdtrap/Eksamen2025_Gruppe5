package org.example.eksamen2025_gruppe5.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String generateUserName(String firstname, String lastName){
        char[] firstNameLetters = firstname.toCharArray();
        char[] lastNameLetters = lastName.toCharArray();

        String firstLetters = firstname.substring(0,2) + lastName.substring(0,2);

        System.out.println(firstNameLetters);
        System.out.println(lastNameLetters);


        return firstLetters.toLowerCase();
    }

    int generateAvailableNumber(String letters){

        //Write code to see who the latest created user with those four letters was

        return 0001; //PLACEHOLDER
    }
}
