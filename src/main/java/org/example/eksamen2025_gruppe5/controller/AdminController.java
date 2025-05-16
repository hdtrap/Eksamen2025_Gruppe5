package org.example.eksamen2025_gruppe5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminGetData")
    public String adminGetData(){
        return "dataregPage";
    }

    @GetMapping("/adminGetRepair")
    public String adminGetRepair(){
        return "damagePage";
    }

}
