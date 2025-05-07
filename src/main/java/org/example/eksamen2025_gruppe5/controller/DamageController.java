package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.model.Damage;
import org.example.eksamen2025_gruppe5.repository.DamageRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class DamageController {


    @Autowired
    private DamageRepository damageRepository;
    @Autowired
    private LeaseRepository leaseRepository;


    @GetMapping("/addDamage")
    public String getAddDamage(){
        return "addDamage";
    }


@PostMapping("/addDamage")
public String addDamage(
        @RequestParam("damageType") String damageType,
        @RequestParam("category") int category,
        @RequestParam("price") double price,
        @RequestParam("leaseId") int leaseId, Model model)
{
    System.out.println("leaseid i addDamage = " + leaseId);
    Damage newDamage = new Damage(leaseId, damageType, category, price);
    //newDamage.setDamageId()
    System.out.println("newDamage oprettet");
    damageRepository.saveDamage(newDamage);
    System.out.println("Damage saved");

    return "redirect:/showDamage?leaseId=" + leaseId;
}

@GetMapping("/getEditDamage")
public String getEditDamage(@RequestParam("damageId") int damageId,
                            @RequestParam("leaseId") int leaseId,
                            Model model,
                            RedirectAttributes redirectAttributes){

    try{
        model.addAttribute("damage", damageRepository.getDamageWithDamageId(damageId));
        model.addAttribute("lease", leaseRepository.findLeaseById(leaseId));
        System.out.println("leaseid i editDamage = " + leaseId);
        return "editDamage";
    }
    catch (LeaseNotFoundException e){
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ leaseId +" blev ikke fundet.");
        return "redirect:/showDamage?leaseId=" + leaseId;
    }

}


@PostMapping("/doEditDamage")
    public String editDamage( @RequestParam("damageType") String damageType,
                                 @RequestParam("category") int category,
                                 @RequestParam("price") double price,
                                 @RequestParam("leaseId") int leaseId, Model model){
    Damage updatedDamage = new Damage(leaseId, damageType, category, price);
    damageRepository.updateDamage(updatedDamage);
    System.out.println("damage er opdateret på lease id " + leaseId );


    return "redirect:/showDamage?leaseId=" + leaseId;
}

@GetMapping("/showDamage")
    public String showDamage(@RequestParam("leaseId") int leaseId, Model damageList,
                             Model lease,
                             RedirectAttributes redirectAttributes){
    System.out.println("leaseid i showDamage = " + leaseId);

    try {
        lease.addAttribute("lease", leaseRepository.findLeaseById(leaseId));
        ArrayList<Damage> damages = damageRepository.getAllDamagesForALeaseWithLeaseId(leaseId);
        damageList.addAttribute("damages", damages);
        return "showDamage";
    }
    catch (LeaseNotFoundException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "Der skete en fejl. Lejeaftale med "+ leaseId +" blev ikke fundet.");
        return "showCar";
    }
}

@PostMapping("/deleteDamage")
    public String deleteDamage(@RequestParam("damageId") int damageId,
                               @RequestParam("leaseId") int leaseId, Model model){
    System.out.println("damageId = " + damageId);

    damageRepository.deleteDamageWithDamageId(damageId);
    System.out.println("Damage deleted");

        return "redirect:/showDamage?leaseId=" + leaseId;
}

@GetMapping("/getShowDamage")
    public String getShowDamage(@RequestParam("leaseId") int leaseId){
        return "redirect:/showDamage?leaseId=" + leaseId;
}




}
