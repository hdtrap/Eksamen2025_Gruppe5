package org.example.eksamen2025_gruppe5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @PostMapping("/saveCreateUser")
    public String saveCreateUser(@RequestParam("userName") String username,
                                 @RequestParam("firstName") String name,
                                 @RequestParam("lastName") String lastname,
                                 @RequestParam("passWord") String password,
                                 @RequestParam("confirmPassWord") String confirmpassword,
                                 RedirectAttributes redirectAttributes){
        System.out.println("information recieved - username: " + username);
        System.out.println("information recieved - username: " + name);
        System.out.println("information recieved - username: " + lastname);
        System.out.println("information recieved - username: " + password);
        System.out.println("information recieved - username: " + confirmpassword);

        return "redirect:/";
    }

    @PostMapping("/loginUser")
    public String tryLoginUser(@RequestParam("userName") String userName,
                               @RequestParam("passWord") String passWord,
                               RedirectAttributes redirectAttributes){
        if(true){
            return "redirect:/getUserPage";
        }
        else {
            return "redirect:/";
        }
    }
}
