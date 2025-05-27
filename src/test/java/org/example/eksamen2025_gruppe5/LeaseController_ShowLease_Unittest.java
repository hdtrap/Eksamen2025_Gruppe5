package org.example.eksamen2025_gruppe5;

import org.example.eksamen2025_gruppe5.controller.LeaseController;
import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.repository.AddOnTypeRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LeaseController_ShowLease_Unittest {
    @Mock
    LeaseRepository leaseRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    AddOnTypeRepository addOnTypeRepository;

    @Mock
    Model model;

    @InjectMocks
    LeaseController leaseController;

    @Mock
    RedirectAttributes redirectAttributes;

    @Test
    public void showLeaseAcceptanceFlow() throws LeaseNotFoundException, UserNotLoggedInException, WrongUserTypeException {
        //Arrange
        int leaseId=1;
        Lease lease=new Lease();

        when(leaseRepository.findLeaseById(leaseId)).thenReturn(lease);
        when(addOnTypeRepository.showSelectedAddons(leaseId)).thenReturn(new ArrayList<>());

        //Act
        String result = leaseController.getLease(String.valueOf(leaseId), model, redirectAttributes);

        //Assert
        assertEquals(result, "showLease");
        verify(model).addAttribute("lease", lease);
    }
}
