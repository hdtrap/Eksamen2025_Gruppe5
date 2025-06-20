package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.*;
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

import java.sql.SQLException;

@Controller
public class UserController {

   @Autowired
    UserService userService;

   @Autowired
    UserRepository userRepository;

   //Mikkel
    @GetMapping("getCreateUser")
    public String getCreateUserPage(Model model)  throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

        if (!model.containsAttribute("userInfo")){
            System.out.println("Der er ikke noget UserInfo, så vi tilføjer en userInfo");
            model.addAttribute("userInfo", new User());
        }
        else{
            System.out.println("Der var Userinfo, så vi tilføjer ikke ny userInfo");
        }
        return "createUserPage";
    }

    //Mikkel
    @PostMapping("/saveCreateUser")
    public String saveCreateUser (@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastname,
                                 @RequestParam("passWord") String password,
                                 @RequestParam("confirmPassWord") String confirmpassword,
                                 @RequestParam("role") String role,
                                 RedirectAttributes redirectAttributes,
                                  Model model) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());


        System.out.println("information recieved - username: " + userService.generateUserName(firstName, lastname));
        System.out.println("information recieved - first name:: " + firstName);
        System.out.println("information recieved - last name: " + lastname);
        System.out.println("information recieved - role: " + role);
        System.out.println("information recieved - password: " + password);
        System.out.println("information recieved - verify password: " + confirmpassword);

        //Check if password inputs match. If not, redirect with appropriate message
        try {
            userRepository.checkPassWordMatch(password, confirmpassword);
        }
        catch (PassWordMismatchException e){
            redirectAttributes.addFlashAttribute("message", "Bruger blev ikke oprettet: Passwords matchede ikke");
            redirectAttributes.addFlashAttribute("userInfo", new User(firstName, lastname, null, null, role.toUpperCase()));
            return "redirect:/getCreateUser";
        }

        //Create the user with the inputs from the page, using the userName generator for the username
        User createdUser = new User(firstName, lastname, userService.generateUserName(firstName, lastname), password, role);

        try {
            userRepository.saveUser(createdUser);
        }
        catch (SQLException e){
            redirectAttributes.addFlashAttribute("message", "Bruger blev ikke oprettet af en ukendt årsag. Prøv igen med andre parametre. fejlKode: " + e.getErrorCode());
            return "redirect:/getCreateUser";
        }

        redirectAttributes.addFlashAttribute("message", "Bruger oprettet med brugernavnet: " + createdUser.getUserName());
        return "redirect:/getUserPage";
    }

    //Mikkel
    @PostMapping("/loginUser")
    public String tryLoginUser(@RequestParam("userName") String userName,
                               @RequestParam("passWord") String passWord,
                               RedirectAttributes redirectAttributes){
        if(userRepository.LogInUserSuccess(userName, passWord)){
            redirectAttributes.addFlashAttribute("message", "hej " + userRepository.getcurrentUser().getUserName());
            System.out.println("User is logging in: " + userRepository.getcurrentUser().getUserName());
            return "redirect:/getUserPage";
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Forkert brugernavn eller password.");
            return "redirect:/";
        }
    }

    //Mikkel
    @PostMapping("/logOutUser")
    public String logOutUser(){
        System.out.println("User is logging out: " + userRepository.getcurrentUser().getUserName());
        userRepository.logOutSession();
        return "redirect:/";
    }

    //Mikkel
    @PostMapping("/getShowUserPage")
    public String getShowUserPage(
            @RequestParam("usernameBox") String username,
            Model model,
            RedirectAttributes redirectAttributes) throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());


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

    //Mikkel
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

    //Mikkel
    @PostMapping("/getEditUser")
    public String editUser(@RequestParam("usernameEdit") String username,
                             Model model,
                             RedirectAttributes redirectAttributes)  throws UserNotLoggedInException, WrongUserTypeException{
        //Verify User is logged in/logged in as the correct type:
        userRepository.verifyLoggedInUser("SYSADMIN");
        //Add the user to the model, to display user relevant items
        model.addAttribute(userRepository.getcurrentUser());

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

    //Mikkel
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

            redirectAttributes.addFlashAttribute("message", "Bruger opdateret");
            return "redirect:/getUserPage";
        }
        catch (UserNotFoundException e){
            System.out.println("Error, user was not found");
            redirectAttributes.addFlashAttribute("message", "Bruger blev ikke opdateret. Kunne ikke finde en bruger med det navn");
            return "redirect:/getUserPage";
        }
        catch (UserNameTakenException e){
            System.out.println("Error, username was taken");
            redirectAttributes.addFlashAttribute("message", "Brugeren kunne ikke opdateres. Brugernavnet er allerede i brug");
            return "redirect:/getUserPage";
        }
        catch (PassWordMismatchException e){
            System.out.println("Error, passwords did not match");
            redirectAttributes.addFlashAttribute("message", "Brugeren blev ikke opdateret. Kodeordene matcher ikke");
            return "redirect:/getUserPage";
        }
    }
}
