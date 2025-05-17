package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/adminGetData")
    public String adminGetData(Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());
        return "dataregPage";
    }

    @GetMapping("/adminGetRepair")
    public String adminGetRepair(Model model) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());
        return "damagePage";
    }

}
