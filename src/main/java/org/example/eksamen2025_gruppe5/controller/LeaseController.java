package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class LeaseController {

    @Autowired
    LeaseRepository leaseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/createLease")
    public String createLease(){
        return "createLease";
    }

    @PostMapping("/saveCreateLease")
    public String postCreateLease(@RequestParam("start_date") LocalDate startDate,
                                  @RequestParam("end_date") LocalDate endDate,
                                  @RequestParam("customer_name") String customerName,
                                  @RequestParam("customer_email") String customerEmail,
                                  @RequestParam("customer_number") String customerNumber,
                                  @RequestParam("price_to_start") Double priceToStart,
                                  @RequestParam("price_pr_month") Double pricePrMonth,
                                  @RequestParam("type_of_lease") String typeOfLease){



            Lease lease = new Lease();
            leaseRepository.saveLease(lease);
        return "redirect:/dataregPage";
    }
    @GetMapping("/showLease")
    public String getLease(@RequestParam("leaseId") int leaseId, Model model) {
        System.out.println("showLease skal blive vist");
        Lease currentLease = leaseRepository.findById(leaseId);
        User currentUser = userRepository.getcurrentUser();

        if (currentUser.isDataReg()) {
            if (currentLease != null) {
                model.addAttribute("lease", currentLease);
            } else {
                System.out.println("Lease ikke fundet " + leaseId);
            }

            return "/showLease";
        }
        System.out.println("bliver vist");
        if (currentUser.isRepair()){
            model.addAttribute("car", currentLease.getCar());
            return "/showCar";
        }
        return "/showLease";
    }
}
