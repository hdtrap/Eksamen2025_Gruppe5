package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController {

    @Autowired
    LeaseRepository leaseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/businessPage")
        public String businessPage(Model model)  throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("BUSINESS");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("revenueFromRentedCars", "Månedlig indtjening fra udlejede biler: " + leaseRepository.monthlyRevenueFromActiveLeases() + " DKK");
        model.addAttribute("noOfLeasedCars", "Antal udlejede biler: " + leaseRepository.noOfLeasedCars());
        model.addAttribute("priceOfLeasedCars", "Samlet værdi af udlejede biler: " + leaseRepository.priceOfLeasedCars() +" DKK");
        model.addAttribute("averageDamageSumPerLease", "Gennemsnitlig skadessum per lease: " + leaseRepository.avgDamageCost() + " DKK");
        return "businessPage";
        }

    @GetMapping("/getKpi")
    public String getKpi(Model model)  throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("BUSINESS");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("revenueFromRentedCars", leaseRepository.monthlyRevenueFromActiveLeases());
        return "showKpi";
    }

}
