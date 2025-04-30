package org.example.eksamen2025_gruppe5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String getLoginpage(){
        return "index";
    }

    @GetMapping("getCreateUser")
    public String getCreateUserPage(){
        return "createUserPage";
    }
}
