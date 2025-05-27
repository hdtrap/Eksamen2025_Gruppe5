package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.model.*;
import org.example.eksamen2025_gruppe5.repository.AddOnTypeRepository;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
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

    //Sarah
    @GetMapping("/createLease")
    public String createLease(Model model) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("DATA");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        //Henter en liste af biler der er tilgængelige til udleje ud fra information i databasen
        ArrayList<Car> availableCars = carRepository.getAvailableCars();
        model.addAttribute("availableCars", availableCars);
        //Henter de mulige tilvalg der kan tilføjes til en lejeaftale ud fra information i databasen
        ArrayList<AddOnType> addOnTypes = addOnTypeRepository.getAllAddOnTypes();
        model.addAttribute("addOnTypes", addOnTypes);
        return "createLease";
    }

    //Sarah
    @PostMapping("/saveCreateLease")
    public String postCreateLease(@RequestParam("vehicle_no") int vehicleNo,
                                  @RequestParam("start_date") LocalDate startDate,
                                  @RequestParam(value = "end_date", required = false) LocalDate endDate,
                                  @RequestParam("customer_name") String customerName,
                                  @RequestParam("customer_email") String customerEmail,
                                  @RequestParam("customer_number") String customerNumber,
                                  @RequestParam("price_to_start") Double priceToStart,
                                  @RequestParam("price_pr_month") Double pricePrMonth,
                                  @RequestParam("type_of_lease") String typeOfLease,
                                  @RequestParam(value = "selectedAddOns", required = false) ArrayList<Integer> selectedAddOns,
                                  RedirectAttributes redirectAttributes){  // modtagede parametre fra html siden

            //Opretter bil objekt ud fra vogn nummer
            Car car = carRepository.findCarByVehicleNumber(vehicleNo);

            // Opretter et lease objekt med slutdato ud fra hvilken abonnementstype der er valgt
            Lease lease;
            if (typeOfLease.equalsIgnoreCase("Limited")){
                endDate = startDate.plusMonths(5);
                lease = new Lease(car, startDate, endDate, customerName, customerEmail, customerNumber, priceToStart, pricePrMonth, typeOfLease);
            }
            else if (endDate == null){
                redirectAttributes.addFlashAttribute("message", "Der skete en fejl, din lejeaftale blev ikke gemt. Prøv igen.");
                return "redirect:/getUserPage";
            } else {
                lease = new Lease(car, startDate, endDate, customerName, customerEmail, customerNumber, priceToStart, pricePrMonth, typeOfLease);
            }

            // Gemmer lease objektet i databasen og modtager det genererede leaseId fra databasen
            int leaseId = leaseRepository.saveLease(lease);

            // Hvis der sker en fejl med at gemme leasen i databasen bliver -1 returneret og brugeren bliver redirected til deres brugerside
            if (leaseId == -1) {
                redirectAttributes.addFlashAttribute("message", "Der skete en fejl, din lejeaftale blev ikke gemt. Prøv igen.");
                return "redirect:/getUserPage";
            }
            // Hvis Addons ikke er null eller tom bliver de tilføjet til lease
            if (selectedAddOns !=null && !selectedAddOns.isEmpty()) {
                addOnTypeRepository.addSelectedAddonsToLease(leaseId, selectedAddOns);
            }
            // redirect til den lease man lige har oprettet
        return "redirect:/showLease?leaseId=" + leaseId;
    }

    //Sarah
    // Viser en lejeaftale
    @GetMapping("/showLease")
    public String getLease(@RequestParam("leaseId") String lease,
                           Model model,
                           RedirectAttributes redirectAttributes)  throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("DATA");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("showLease skal blive vist");

        try {
            if (lease.matches("\\d+")) {
                Lease currentLease = leaseRepository.findLeaseById(Integer.parseInt(lease));
                System.out.println("Fundet lease: " + currentLease);

                model.addAttribute("lease", currentLease);
                ArrayList<AddOnType> selectedAddons = addOnTypeRepository.showSelectedAddons(Integer.parseInt(lease));
                model.addAttribute("selectedAddons", selectedAddons);
                return "showLease";
            }
            else {
                Lease currentLease = leaseRepository.findLeaseByCustomerName(lease);
                System.out.println("Fundet lease: " + currentLease);

                model.addAttribute("lease", currentLease);
                ArrayList<AddOnType> selectedAddons = addOnTypeRepository.showSelectedAddons(currentLease.getLeaseId());
                model.addAttribute("selectedAddons", selectedAddons);
                return "showLease";
            }

        }
        catch (LeaseNotFoundException e){
            e.printStackTrace();
            System.out.println("Lease ikke fundet " + lease);
            redirectAttributes.addFlashAttribute("message", "Kunne ikke finde den lejeaftale du ledte efter.");
            return "redirect:/getUserPage"; // skal redirect til skadeside hvis isRepair
        }

    }

    //Frederik
    @GetMapping("/showCarWithLeaseID")
    public String showCarWithLeaseID(@RequestParam("leaseId") int leaseId, Model model,
                                     RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("Fundet lease: " + leaseId);

        return "redirect:/showCar?leaseId=" + leaseId; //?leaseId sender en paramater med i url så jeg kan hente leaseId i CarControlleren
    }

    //Sarah
    // Viser siden til at redigere en lejeaftale
    @GetMapping("/getUpdateLease")
    public String updateLease(@RequestParam("leaseId") int id, Model model,
                              RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("DATA");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        try {
            Lease lease = leaseRepository.findLeaseById(id);
            model.addAttribute("lease", lease);
            ArrayList<AddOnType> addOnTypes = addOnTypeRepository.getAllAddOnTypes();
            model.addAttribute("addOnTypes", addOnTypes);
            return "editLease";
        }
        catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ id +" blev ikke fundet.");
            return "redirect:/showLease";
        }
    }

    //Sarah
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
            @RequestParam(value = "selectedAddOns", required = false) ArrayList<Integer> selectedAddOns,
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

            if (selectedAddOns !=null && !selectedAddOns.isEmpty()) {
                addOnTypeRepository.updateSelectedAddonsOnLease(lease.getLeaseId(), selectedAddOns);
            }
            return "redirect:/showLease?leaseId=" + id;
        }
        catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Lejeaftale med id "+ id +" ikke fundet, ingen ændringer blev gemt.");
            return "redirect:/showLease";
        }
    }

    //Sarah
    // Sletter en lease ud fra id
    @PostMapping("/deleteLease")
    public String deleteLease(@RequestParam("id") int id) throws LeaseNotFoundException {
        leaseRepository.deleteLease(id);
        return "redirect:/getUserPage";
    }

    //Mikkel, Peter
    @GetMapping("/getShowAllLeases")
    public String getShowAllLeases(Model model)throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("ANY");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("Trying to show leases");
        model.addAttribute("listOfAllLeases", leaseRepository.getAllLeases());
        model.addAttribute("listOfActiveLeases", leaseRepository.getAllActiveLeases());

        return "showAllLeases";
    }
}
