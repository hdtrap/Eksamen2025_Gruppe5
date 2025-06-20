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
public class PageController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private LeaseRepository leaseRepository;

    //Mikkel
    @GetMapping("/")
    public String getLoginpage(){
        return "index";
    }

    //Mikkel
    @GetMapping("/getUserPage")
    public String getUserPage(Model model)  throws UserNotLoggedInException, WrongUserTypeException{

        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        if(userRepository.getcurrentUser().isAdmin()){
            model.addAttribute("isAdmin", "this user is admin");

            model.addAttribute("notificationList", notificationRepository.getAdminNotifications());
            return "adminPage";
        }
        if(userRepository.getcurrentUser().isDataReg()){
            model.addAttribute("Data", "this user is data registration");
            model.addAttribute("notificationList", notificationRepository.getDataRegNotifications());
            return "dataregPage";
        }
        if(userRepository.getcurrentUser().isRepair()){
            model.addAttribute("isRepair", "this user is repair");
            model.addAttribute("notificationList", notificationRepository.getRepairNotifications());
            return "damagePage";
        }
        if(userRepository.getcurrentUser().isBusiness()){
            model.addAttribute("isBusiness", "this user is business");
            model.addAttribute("notificationList", notificationRepository.getBusinessNotifications());

            // Add KPIs
            model.addAttribute("revenueFromRentedCars", leaseRepository.monthlyRevenueFromActiveLeases());
            model.addAttribute("noOfLeasedCars", leaseRepository.noOfLeasedCars());
            model.addAttribute("totalCars", carRepository.totalCars());
            model.addAttribute("priceOfLeasedCars", leaseRepository.priceOfLeasedCars());
            model.addAttribute("averageDamageSumPerLease", leaseRepository.avgDamageCost());
            model.addAttribute("mostCommonCarModel", carRepository.mostCommonModel().toString());
            return "businessPage";
        }
        else{
            model.addAttribute("message", "Kunne ikke logge ind. Brugeren har ingen type.");
            return "/";
        }
    }

    //Sarah
    @GetMapping("/dataregPage")
    public String showDataRegPage(Model model)throws UserNotLoggedInException, WrongUserTypeException{
        userRepository.verifyLoggedInUser("DATA");
        model.addAttribute(userRepository.getcurrentUser());
        model.addAttribute("notificationList", notificationRepository.getDataRegNotifications());
        return "dataregPage";
    }

    //Peter
    @GetMapping("/getShowListsOfCars")
    public String getShowListOfCars(Model model)throws UserNotLoggedInException, WrongUserTypeException{
        userRepository.verifyLoggedInUser("ANY");
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("Registreret prøver at vise biler");
        model.addAttribute("listOfAllCars", carRepository.getAllCars());
        model.addAttribute("listOfLeasedCars", carRepository.getLeasedCars());
        model.addAttribute("listOfNonLeasedCars", carRepository.getNonLeasedCars());

        return "showListsOfCars";
    }

}
