package org.example.eksamen2025_gruppe5.controller;

import org.example.eksamen2025_gruppe5.model.Damage;
import org.example.eksamen2025_gruppe5.repository.DamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageController {


    @Autowired
    private DamageRepository damageRepository;


    @GetMapping("/addDamage")
    public String getAddDamage(){
        return "addDamage";
    }


@PostMapping("/addDamage")
public String addDamage(
        @RequestParam("damageType") String damageType,
        @RequestParam("category") int category,
        @RequestParam("price") double price)
{
    System.out.println("Vi loader parameterne");
    Damage newDamage = new Damage(damageType, category, price);
    //newDamage.setLeaseId(currentLease.getId);
    //newDamage.setDamageId()
    System.out.println("newDamage oprettet");
    damageRepository.saveDamage(newDamage);
    System.out.println("Damage saved");

    return "redirect:/addDamage";
}
@GetMapping("getEditDamage")
    public String getEditDamage(Model damage){

    return "editDamage";
}




}
