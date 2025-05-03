package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.PassWordMismatchException;
import org.example.eksamen2025_gruppe5.exceptions.UserNameTakenException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotFoundException;
import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.example.eksamen2025_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


        redirectAttributes.addFlashAttribute("message", "Bruger oprettet med brugernavnet: " + createdUser.getUserName());
        return "redirect:/getUserPage";
    }

    @PostMapping("/loginUser")
    public String tryLoginUser(@RequestParam("userName") String userName,
                               @RequestParam("passWord") String passWord,
                               RedirectAttributes redirectAttributes){
        if(userRepository.LogInUserSuccess(userName, passWord)){
            redirectAttributes.addFlashAttribute("message", "hej " + userRepository.getcurrentUser().getUserName());
            return "redirect:/getUserPage";
        }
        else {
            return "redirect:/";
        }
    }

    @GetMapping("/getUserPage")
    public String getUserPage(Model model){
        if(userRepository.getcurrentUser().isAdmin()){
            model.addAttribute("isAdmin", "this user is admin");
        }
        return "userPage";
    }

    @PostMapping("/getShowUserPage")
    public String getShowUserPage(
            @RequestParam("usernameBox") String username,
            Model model,
            RedirectAttributes redirectAttributes){
        System.out.println("Prøver at finde en bruger");

        try{
            User foundUser = userRepository.findUserByUserName(username);
            System.out.println("Har fundet: " + foundUser.getUserName());
            model.addAttribute("user", foundUser);
            return "showUser";
        }

        catch (UserNotFoundException e){
            e.printStackTrace();
            System.out.println("Fandt ikke en bruger med det navn");
            redirectAttributes.addFlashAttribute("message", "Kunne ikke finde en bruger med det brugernavn");
            return "redirect:/getUserPage";
        }
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("usernameDelete") String username,
                             Model model,
                             RedirectAttributes redirectAttributes){
        try {
            userRepository.deleteUserByUserName(username);
            redirectAttributes.addFlashAttribute("message", "user with username " + username + " was deleted");
            return "redirect:/getUserPage";
        }
        catch (UserNotFoundException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "could not delete user: " + username + " because the user could not be found");
        }

        return "redirect:/getUserPage";
    }

    @PostMapping("/getEditUser")
    public String editUser(@RequestParam("usernameEdit") String username,
                             Model model,
                             RedirectAttributes redirectAttributes){
        try {
            User userForEdit = userRepository.findUserByUserName(username);
            model.addAttribute("user", userForEdit);
            return "editUser";
        }
        catch (UserNotFoundException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "There was an error. User with the username: " + username + " could not be found");
            return "redirect:/getUserPage";
            }
    }

    @PostMapping("/saveUpdateUser")
    public String saveEditUser(
            @RequestParam("userName") String newUserName,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("passWord") String passWord,
            @RequestParam("passWordControl") String passWordControl,
            @RequestParam("role") String role,
            @RequestParam("oldUserName") String oldUserName,
            Model model,
            RedirectAttributes redirectAttributes){


        System.out.println("Recieved info: " + newUserName + firstName + lastName + passWord + passWordControl + role + oldUserName);
        try{
            userRepository.checkPassWordMatch(passWord, passWordControl);
            System.out.println("passwords match");
            role = role.toUpperCase();
            System.out.println("Role set to = " + role);
            userRepository.updateUser(oldUserName, newUserName, firstName, lastName, passWord, role);

            redirectAttributes.addFlashAttribute("message", "User has been modified");
            return "redirect:/getUserPage";
        }
        catch (UserNotFoundException e){
            System.out.println("Error, user was not found");
            redirectAttributes.addFlashAttribute("message", "User was not updated. Could not find the user.");
            return "redirect:/getUserPage";
        }
        catch (UserNameTakenException e){
            System.out.println("Error, username was taken");
            redirectAttributes.addFlashAttribute("message", "User could not be modified. Username is already in use");
            return "redirect:/getUserPage";
        }
        catch (PassWordMismatchException e){
            System.out.println("Error, passwords did not match");
            redirectAttributes.addFlashAttribute("message", "User was not updated. New passwords did not match");
            return "redirect:/getUserPage";
        }
    }
}
