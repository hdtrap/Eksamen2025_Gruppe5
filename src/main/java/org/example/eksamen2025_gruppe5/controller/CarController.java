package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {

    @Autowired
    LeaseRepository leaseRepository;
    @Autowired
    CarRepository carRepository;


@GetMapping("/showCar") // Jeg requester leaseId tilføjer både lease og car
    public String showCar(@RequestParam("leaseId") int leaseId, Model model) {
    Lease lease = leaseRepository.findById(leaseId);
        model.addAttribute("car", lease.getCar());
        model.addAttribute("lease", lease);
    return "showCar";
}
@GetMapping("/getAddDamage")
    public String addDamage(@RequestParam("vehicleNumber") int vehicleNumber,
                            @RequestParam("leaseId") int leaseId, Model model){
    System.out.println("vehicleNumber: " + vehicleNumber);
    if (model != null) {
        Car car = carRepository.findCarByVehicleNumber(vehicleNumber);
        model.addAttribute("car", car);
        model.addAttribute("lease", leaseRepository.findById(leaseId));
    }
    System.out.println("model: " + model);

    return "addDamage";
}







}
