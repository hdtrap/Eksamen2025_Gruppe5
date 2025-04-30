package org.example.eksamen2025_gruppe5.service;

import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateUserName(String firstname, String lastName){
        char[] firstNameLetters = firstname.toCharArray();
        char[] lastNameLetters = lastName.toCharArray();

        String firstLetters = firstname.substring(0,2) + lastName.substring(0,2);
        firstLetters = firstLetters.toLowerCase();

        System.out.println(firstNameLetters);
        System.out.println(lastNameLetters);

        String fullUserName = firstLetters + userRepository.findNumberForUsername(firstLetters);
        return fullUserName;
    }

    int generateAvailableNumber(String letters){

        //Write code to see who the latest created user with those four letters was

        return 0001; //PLACEHOLDER
    }
}
