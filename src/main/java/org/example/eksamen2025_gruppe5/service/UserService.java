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

        String firstLetters = firstname.substring(0,2) + lastName.substring(0,2);
        firstLetters = firstLetters.toLowerCase();

        String fullUserName = firstLetters + userRepository.findNumberForUsername(firstLetters);
        return fullUserName;
    }


}
