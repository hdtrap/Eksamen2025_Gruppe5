package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.model.Damage;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.example.eksamen2025_gruppe5.repository.DamageRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class DamageController {


    @Autowired
    private DamageRepository damageRepository;
    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;

//Frederik
    @GetMapping("/addDamage")
    public String getAddDamage() {
        return "addDamage";
    }

    //Frederik
    @PostMapping("/addDamage")
    public String addDamage(
            @RequestParam("damageType") String damageType,
            @RequestParam("category") int category,
            @RequestParam("price") double price,
            @RequestParam("leaseId") int leaseId, Model model) throws UserNotLoggedInException, WrongUserTypeException, LeaseNotFoundException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items

        model.addAttribute(userRepository.getcurrentUser());
        if (damageType.trim().isEmpty()) { // Tjekker om navnet bare er et mellem
            model.addAttribute("error", "Skadetype må ikke være tom.");
            Lease lease = leaseRepository.findLeaseById(leaseId);
            model.addAttribute("lease", lease);
            Car car = carRepository.findCarByVehicleNumber(lease.getCar().getVehicleNumber());
            model.addAttribute("car", car);
            return "addDamage";
        }

        System.out.println("leaseid i addDamage = " + leaseId);
        Damage newDamage = new Damage(leaseId, damageType, category, price);
        System.out.println("newDamage oprettet");
        damageRepository.saveDamage(newDamage);
        System.out.println("Damage saved");

        return "redirect:/showDamage?leaseId=" + leaseId;
    }


    //Frederik
    @GetMapping("/getEditDamage")
    public String getEditDamage(@RequestParam("damageId") int damageId,
                                @RequestParam("leaseId") int leaseId,
                                Model model,
                                RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        try {
            model.addAttribute("damage", damageRepository.getDamageWithDamageId(damageId));
            model.addAttribute("lease", leaseRepository.findLeaseById(leaseId));
            System.out.println("leaseid i editDamage = " + leaseId);
            return "editDamage";
        } catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med " + leaseId + " blev ikke fundet.");
            return "redirect:/showDamage?leaseId=" + leaseId;
        }
    }

    //Frederik
    @PostMapping("/doEditDamage")
    public String editDamage(@RequestParam("damageType") String damageType,
                             @RequestParam("category") int category,
                             @RequestParam("price") double price,
                             @RequestParam("leaseId") int leaseId,
                             @RequestParam("damageId") int damageId,
                             Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        Damage updatedDamage = new Damage(damageId, leaseId, damageType, category, price);
        damageRepository.updateDamage(updatedDamage);
        System.out.println("damage er opdateret på lease id " + leaseId);

        return "redirect:/showDamage?leaseId=" + leaseId;
    }

    //Frederik
    @GetMapping("/showDamage")
    public String showDamage(@RequestParam("leaseId") int leaseId, Model damageList,
                             Model model,
                             RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("leaseid i showDamage = " + leaseId);
        try {
            model.addAttribute("lease", leaseRepository.findLeaseById(leaseId));
            ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
            damageList.addAttribute("damages", damages);
            return "showDamage";
        } catch (LeaseNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med " + leaseId + " blev ikke fundet.");
            return "showCar";
        }
    }

    //Frederik
    @PostMapping("/deleteDamage")
    public String deleteDamage(@RequestParam("damageId") int damageId,
                               @RequestParam("leaseId") int leaseId, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("damageId = " + damageId);

        damageRepository.deleteDamageWithDamageId(damageId);
        System.out.println("Damage deleted");

        return "redirect:/showDamage?leaseId=" + leaseId;
    }

    //Frederik
    @GetMapping("/getShowDamage")
    public String getShowDamage(@RequestParam("leaseId") int leaseId) {
        return "redirect:/showDamage?leaseId=" + leaseId;
    }

    //Frederik
    @GetMapping("/getPrintDamageReport")
    public String getPrintDamageReport(@RequestParam("leaseId") int leaseId, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        System.out.println("du er nu i getPrintDamageReport leaseid: " + leaseId);
        model.addAttribute("leaseId", leaseId);
        ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
        model.addAttribute("damages", damages);
        return "printDamageReport";
    }

    //Frederik
    @GetMapping("/printDamageReport")
    // Denne metode printer skaderapporter i en mappe i systemet der hedder skaderapport
    public String printDamageReport(@RequestParam("leaseId") int leaseId, Model model,
                                    RedirectAttributes redirectAttributes) throws IOException, UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("leaseId", leaseId);
        System.out.println("du er nu i printDamageReport leaseid: " + leaseId);

        try {
            Lease lease = leaseRepository.findLeaseById(leaseId);
            damageRepository.printSkadesRapport(lease);

        } catch (LeaseNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "printDamageReport";
    }

    //Frederik
    @GetMapping("/getFixDamage")
    public String getFixDamage(@RequestParam("leaseId") int leaseId,
                               @RequestParam("vehicleNumber") int vehicleNumber, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("leaseId", leaseId);
        ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
        model.addAttribute("damages", damages);
        model.addAttribute("car", carRepository.findCarByVehicleNumber(vehicleNumber));

        return "fixDamage";
    }

    //Frederik
    @PostMapping("/fixDamage")
    public String fixDamage(@RequestParam("leaseId") int leaseId,
                            @RequestParam("damageId") int damageID,
                            @RequestParam("isFixed") boolean isFixed,
                            @RequestParam("vehicleNumber") int vehicleNumber, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        Damage damage = damageRepository.getDamageWithDamageId(damageID);
        model.addAttribute("damage", damage);

        boolean newFixedBoolean = !isFixed; //Et statement som gør newFixedBoolean til det modsatte af isFixed

        damageRepository.fixDamage(newFixedBoolean, damageID);
        model.addAttribute("leaseId", leaseId);
        ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
        model.addAttribute("damages", damages);
        model.addAttribute("car", carRepository.findCarByVehicleNumber(vehicleNumber));

        return "fixDamage";
    }

    //Frederik
    @GetMapping("/getPayDamages")
    public String getPayDamages(@RequestParam("leaseId") int leaseId, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("DATA");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("leaseId", leaseId);
        model.addAttribute("damages", damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId));

        return "payDamages";
    }

    //Frederik
    @PostMapping("/payDamages")
    public String payDamages(@RequestParam("leaseId") int leaseId, Model model) throws UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("DATA");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        model.addAttribute("leaseId", leaseId);
        System.out.println(leaseId + "leaseId i payDamage");
        damageRepository.payAllDamagesOnALease(leaseId);
        model.addAttribute("damages", damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId));

        return "payDamages";
    }
}

