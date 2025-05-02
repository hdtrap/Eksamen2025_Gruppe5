package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class LeaseController {

    @Autowired
    LeaseRepository leaseRepository;

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
}
