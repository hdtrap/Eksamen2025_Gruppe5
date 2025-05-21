package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.NotificationRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    LeaseRepository leaseRepository;

    @Autowired
    CarRepository carRepository;

    public AdminController(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/adminGetData")
    public String adminGetData(Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        //Notifications
        model.addAttribute("notificationList", notificationRepository.getDataRegNotifications());
        return "dataregPage";
    }

    @GetMapping("/adminGetRepair")
    public String adminGetRepair(Model model) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("notificationList", notificationRepository.getRepairNotifications());
        return "damagePage";
    }

    @GetMapping("/adminGetBusiness")
    public String adminGetBusiness(Model model) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        // Add KPIs
        model.addAttribute("revenueFromRentedCars", "Månedlig indtjening fra udlejede biler: " + leaseRepository.monthlyRevenueFromActiveLeases() + " DKK");
        model.addAttribute("noOfLeasedCars", "Antal udlejede biler: " + leaseRepository.noOfLeasedCars());
        model.addAttribute("totalCars", "Antal biler i alt: " + carRepository.totalCars());
        model.addAttribute("priceOfLeasedCars", "Samlet værdi af udlejede biler: " + leaseRepository.priceOfLeasedCars() +" DKK");
        model.addAttribute("averageDamageSumPerLease", "Gennemsnitlig skadessum per lease: " + leaseRepository.avgDamageCost() + " DKK");
        model.addAttribute("mostCommonCarModel", "Mest populære bilmodel: " + carRepository.mostCommonModel().toString());

        model.addAttribute("notificationListe", notificationRepository.getBusinessNotifications());
        return "businessPage";
    }

}
