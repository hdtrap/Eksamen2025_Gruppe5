package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.model.*;
import org.example.eksamen2025_gruppe5.repository.AddOnTypeRepository;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.example.eksamen2025_gruppe5.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class LeaseController {

    @Autowired
    LeaseRepository leaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    AddOnTypeRepository addOnTypeRepository;

    @Autowired
    LeaseService leaseService;

    @GetMapping("/createLease")
    public String createLease(Model model){
        ArrayList<Car> availableCars = carRepository.getAvailableCars();
        model.addAttribute("availableCars", availableCars);
        ArrayList<AddOnType> addOnTypes = addOnTypeRepository.getAllAddOnTypes();
        model.addAttribute("addOnTypes", addOnTypes);
        return "createLease";
    }

    @PostMapping("/saveCreateLease")
    public String postCreateLease(@RequestParam("vehicle_no") int vehicleNo,
                                  @RequestParam("start_date") LocalDate startDate,
                                  @RequestParam("end_date") LocalDate endDate,
                                  @RequestParam("customer_name") String customerName,
                                  @RequestParam("customer_email") String customerEmail,
                                  @RequestParam("customer_number") String customerNumber,
                                  @RequestParam("price_to_start") Double priceToStart,
                                  @RequestParam("price_pr_month") Double pricePrMonth,
                                  @RequestParam("type_of_lease") String typeOfLease,
                                  @RequestParam("selectedAddOns") ArrayList<Integer> selectedAddOns,
                                  RedirectAttributes redirectAttributes){

            Car car = carRepository.findCarByVehicleNumber(vehicleNo);

            Lease lease = new Lease(car, startDate, endDate, customerName, customerEmail, customerNumber, priceToStart, pricePrMonth, typeOfLease);
            int leaseId = leaseRepository.saveLease(lease);

            if (leaseId == -1) {
                redirectAttributes.addFlashAttribute("message", "Der skete en fejl, din lejeaftale blev ikke gemt. Prøv igen.");
                return "redirect:/dataregPage";
            }
            if (selectedAddOns !=null && !selectedAddOns.isEmpty()) {
                leaseService.addSelectedAddonsToLease(lease.getLeaseId(), selectedAddOns);
            }
        return "redirect:/dataregPage";
    }

    // Viser en lejeaftale
    @GetMapping("/showLease")
    public String getLease(@RequestParam("leaseId") int leaseId, Model model,
                           RedirectAttributes redirectAttributes) {
        System.out.println("showLease skal blive vist");

        try {
            Lease currentLease = leaseRepository.findLeaseById(leaseId);
            System.out.println("Fundet lease: " + currentLease);
            User currentUser = userRepository.getcurrentUser();

            if (currentUser.isDataReg()) {
                model.addAttribute("lease", currentLease);
                return "showLease";
            }

            System.out.println("bliver vist");
            if (currentUser.isRepair()){
                model.addAttribute("lease", currentLease);
                return "redirect:/showCar?leaseId=" + currentLease.getLeaseId(); //?leaseId + currentLease.getLeaseId() sender en paramater med i url så jeg kan hente lease i CarControlleren
            }

            else redirectAttributes.addFlashAttribute("message", "Du har ikke adgang til denne side.");
            return "redirect:/dataregPage";
        }
        catch (LeaseNotFoundException e){
            e.printStackTrace();
            System.out.println("Lease ikke fundet " + leaseId);
            redirectAttributes.addFlashAttribute("message", "Kunne ikke finde en lejeaftale med id "+ leaseId +".");
            return "redirect:/getUserPage"; // skal redirect til skadeside hvis isRepair
        }

    }

    // Viser siden til at redigere en lejeaftale
    @GetMapping("/getUpdateLease")
    public String updateLease(@RequestParam("id") int id, Model model,
                              RedirectAttributes redirectAttributes){

        try {
            Lease lease = leaseRepository.findLeaseById(id);
            model.addAttribute("lease", lease);
            return "editLease";
        }
        catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ id +" blev ikke fundet.");
            return "redirect:/showLease";
        }
    }

    // Håndterer indsendelsen af formularen til at opdatere en lejeaftale
    @PostMapping("/saveUpdateLease")
    public String postUpdateLease(
            @RequestParam("id") int id,
            @RequestParam("vehicle_no") int vehicleNo,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_email") String customerEmail,
            @RequestParam("customer_number") String customerNumber,
            @RequestParam("price_to_start") Double priceToStart,
            @RequestParam("price_pr_month") Double pricePrMonth,
            @RequestParam("type_of_lease") String typeOfLease,
            RedirectAttributes redirectAttributes){

        try {
            Lease lease = leaseRepository.findLeaseById(id);
            lease.setCar(carRepository.findCarByVehicleNumber(vehicleNo));
            lease.setStartDate(startDate);
            lease.setEndDate(endDate);
            lease.setCustomerName(customerName);
            lease.setCustomerEmail(customerEmail);
            lease.setCustomerNumber(customerNumber);
            lease.setPriceToStart(priceToStart);
            lease.setPricePrMonth(pricePrMonth);
            lease.setTypeOfLease(TypeOfLease.valueOf(typeOfLease));
            leaseRepository.updateLease(lease);
            return "redirect:/showLease?leaseId=" + id;
        }
        catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Lejeaftale med id "+ id +" ikke fundet, ingen ændringer blev gemt.");
            return "redirect:/showLease";
        }
    }

    // Sletter et ønske ud fra id
    @PostMapping("/deleteLease")
    public String deleteLease(@RequestParam("id") int id){
        leaseRepository.deleteLease(id);
        return "redirect:/dataregPage";
    }
}
