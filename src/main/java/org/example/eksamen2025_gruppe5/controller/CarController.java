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

import java.util.ArrayList;

@Controller
public class CarController {

    @Autowired
    LeaseRepository leaseRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DamageRepository damageRepository;

//Frederik
@GetMapping("/showCar") // Jeg requester leaseId tilføjer både lease og car
    public String showCar(@RequestParam("leaseId") int leaseId, Model model,
                          RedirectAttributes redirectAttributes)  throws UserNotLoggedInException, WrongUserTypeException {
    //Verify User is logged in/logged in as the correct type:
    userRepository.verifyLoggedInUser("REPAIR");
    //Add the user to the model, to display user relevant items
    model.addAttribute(userRepository.getcurrentUser());
    try {
        Lease lease = leaseRepository.findLeaseById(leaseId);
        model.addAttribute("car", lease.getCar());
        model.addAttribute("lease", lease);
        return "showCar";
    } catch (LeaseNotFoundException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ leaseId +" blev ikke fundet.");
        return "redirect:/showLease";
    }
}

//Frederik
@GetMapping("/getAddDamage")
    public String addDamage(@RequestParam("vehicleNumber") int vehicleNumber,
                            @RequestParam("leaseId") int leaseId, Model model,
                            RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException{
    //Verify User is logged in/logged in as the correct type:
    userRepository.verifyLoggedInUser("REPAIR");
    //Add the user to the model, to display user relevant items
    model.addAttribute(userRepository.getcurrentUser());

    System.out.println("vehicleNumber: " + vehicleNumber);

    try {
        Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
        model.addAttribute("car", car);
        model.addAttribute("lease", leaseRepository.findLeaseById(leaseId));
        System.out.println("model: " + model);

        return "addDamage";
    }
    catch (LeaseNotFoundException e){
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ leaseId +" blev ikke fundet.");
        return "redirect:/showCar";
    }
}

//Frederik
    @PostMapping("/changeCarStatusAvailable") // Kaldet i fixDamage.html
    public String changeCarStatusAvailable(@RequestParam("leaseId") int leaseId,
                                  @RequestParam("vehicleNo") int vehicleNo, Model model) throws LeaseNotFoundException, UserNotLoggedInException, WrongUserTypeException {

        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

    Car car = carRepository.findCarByVehicleNumber(vehicleNo);
    carRepository.makeCarStatusAvailable(car);
    model.addAttribute("leaseId", leaseId);
    model.addAttribute("car", carRepository.findCarByVehicleNumber(vehicleNo));
    ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
    model.addAttribute("damages", damages);


        return "fixDamage";
    }
//Frederik
    @PostMapping("/changeCarStatusGettingRepaired") // Kaldet i fixDamage.html
    public String changeCarStatusGettingRepaired(@RequestParam("leaseId") int leaseId,
                                           @RequestParam("vehicleNo") int vehicleNo, Model model) throws LeaseNotFoundException, UserNotLoggedInException, WrongUserTypeException {

        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        Car car = carRepository.findCarByVehicleNumber(vehicleNo);
        carRepository.makeCarStatusGettingRepaired(car);
        model.addAttribute("leaseId", leaseId);
        model.addAttribute("car", carRepository.findCarByVehicleNumber(vehicleNo));
        ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
        model.addAttribute("damages", damages);

        return "fixDamage";
    }

    //Frederik
    @PostMapping("/changeCarStatusPendingEvaluation") // Kaldet i fixDamage.html
    public String changeCarStatusPendingEvaluation(@RequestParam("leaseId") int leaseId,
                                                 @RequestParam("vehicleNo") int vehicleNo, Model model) throws LeaseNotFoundException, UserNotLoggedInException, WrongUserTypeException {

        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        Car car = carRepository.findCarByVehicleNumber(vehicleNo);
        carRepository.makeCarStatusPendingEvaluation(car);
        Lease lease = leaseRepository.findLeaseById(leaseId);
        model.addAttribute("car", lease.getCar());
        model.addAttribute("lease", lease);

        return "showCar";
    }

    //Frederik
    @PostMapping("/changeCarStatusSold") // Kaldet i fixDamage.html
    public String changeCarStatusSold(@RequestParam("leaseId") int leaseId,
                                @RequestParam("vehicleNo") int vehicleNo, Model model) throws LeaseNotFoundException, UserNotLoggedInException, WrongUserTypeException {
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("REPAIR");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        Car car = carRepository.findCarByVehicleNumber(vehicleNo);
        carRepository.makeCarStatusSold(car);
        Lease lease = leaseRepository.findLeaseById(leaseId);
        model.addAttribute("car", lease.getCar());
        model.addAttribute("lease", lease);

        return "showCar";
    }
}
