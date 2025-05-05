package org.example.eksamen2025_gruppe5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {

@GetMapping("/showCar")
    public String showCar(Model carModel) {

   // carModel.

    return "showCar";
}







}
