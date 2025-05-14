package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController {

    @Autowired
    LeaseRepository leaseRepository;

    @GetMapping("/businessPage")
        public String businessPage(Model model) {
        model.addAttribute("revenueFromRentedCars", "Månedlig indtjening fra udlejede biler: " + leaseRepository.monthlyRevenueFromActiveLeases() + " DKK");
        model.addAttribute("noOfLeasedCars", "Antal udlejede biler: " + leaseRepository.noOfLeasedCars());
        model.addAttribute("priceOfLeasedCars", "Samlet værdi af udlejede biler: " + leaseRepository.priceOfLeasedCars() +" DKK");
        return "businessPage";
        }

    @GetMapping("/getKpi")
    public String getKpi(Model model) {
        model.addAttribute("revenueFromRentedCars", leaseRepository.monthlyRevenueFromActiveLeases());
        return "showKpi";
    }

}
