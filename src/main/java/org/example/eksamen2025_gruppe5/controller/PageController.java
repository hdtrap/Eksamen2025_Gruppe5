package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String getLoginpage(){
        return "index";
    }

    @GetMapping("/getUserPage")
    public String getUserPage(Model model){
        if(userRepository.getcurrentUser().isAdmin()){
            model.addAttribute("isAdmin", "this user is admin");
            return "adminPage";
        }
        if(userRepository.getcurrentUser().isDataReg()){
            model.addAttribute("Data", "this user is data registration");
            return "dataregPage";
        }
        if(userRepository.getcurrentUser().isRepair()){
            model.addAttribute("isRepair", "this user is repair");
            return "damagePage";
        }
        if(userRepository.getcurrentUser().isBusiness()){
            model.addAttribute("isBusiness", "this user is business");
            return "redirect:/businessPage";
        }
        else{
            model.addAttribute("message", "Could not log in: User has no usertype");
            return "/";
        }
    }
    @GetMapping("/dataregPage")
    public String showDataRegPage(){
        return "dataregPage";
    }
}
