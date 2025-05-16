package org.example.eksamen2025_gruppe5.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotLoggedInException.class)
    public String handleNotLoggedIn(RedirectAttributes redirectAttributes){
        System.out.println();
        redirectAttributes.addFlashAttribute("message", "du er ikke logget ind");
        return "redirect:/";
    }

    @ExceptionHandler(WrongUserTypeException.class)
    public String handleWrongUser(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "Du er ikke den rigtige type bruger");
        return "redirect:/getUserPage";
    }

}
