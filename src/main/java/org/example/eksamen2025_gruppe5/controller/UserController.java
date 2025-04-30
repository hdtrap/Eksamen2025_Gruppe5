package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.example.eksamen2025_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

   @Autowired
    UserService userService;

   @Autowired
    UserRepository userRepository;

    @PostMapping("/saveCreateUser")
    public String saveCreateUser(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastname,
                                 @RequestParam("passWord") String password,
                                 @RequestParam("confirmPassWord") String confirmpassword,
                                 @RequestParam("role") String role,
                                 RedirectAttributes redirectAttributes){
        System.out.println("information recieved - username: " + userService.generateUserName(firstName, lastname));
        System.out.println("information recieved - first name:: " + firstName);
        System.out.println("information recieved - last name: " + lastname);
        System.out.println("information recieved - role: " + role);
        System.out.println("information recieved - password: " + password);
        System.out.println("information recieved - verify password: " + confirmpassword);

        if (password != confirmpassword){
            System.out.println("WRONG PASSWORD");
            //Redirect to try again page with redirect attributes
        }

        //Create the user with the inputs from the page
        User createdUser = new User(firstName, lastname, userService.generateUserName(firstName, lastname), password, role);

        userRepository.saveUser(createdUser);

        redirectAttributes.addFlashAttribute("message", "hej " + createdUser.getFirstName());
        return "redirect:/getUserPage";
    }

    @PostMapping("/loginUser")
    public String tryLoginUser(@RequestParam("userName") String userName,
                               @RequestParam("passWord") String passWord,
                               RedirectAttributes redirectAttributes){
        if(true){
            redirectAttributes.addFlashAttribute("message", "hej " + userName);
            return "redirect:/getUserPage";
        }
        else {
            return "redirect:/";
        }
    }

    @GetMapping("/getUserPage")
    public String getUserPage(){
        return "showUser";
    }
}
