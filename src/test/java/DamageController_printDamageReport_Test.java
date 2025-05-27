import org.example.eksamen2025_gruppe5.controller.DamageController;
import org.example.eksamen2025_gruppe5.exceptions.LeaseNotFoundException;
import org.example.eksamen2025_gruppe5.exceptions.UserNotLoggedInException;
import org.example.eksamen2025_gruppe5.exceptions.WrongUserTypeException;
import org.example.eksamen2025_gruppe5.model.Lease;
import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.repository.DamageRepository;
import org.example.eksamen2025_gruppe5.repository.LeaseRepository;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@ExtendWith(MockitoExtension.class)
public class DamageController_printDamageReport_Test {

    private MockMvc mockMvc;
    @Mock
    private UserRepository userRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private DamageRepository damageRepository;

    @Test
    void printDamageReportTest() throws Exception {
        int leaseId = 1;
        Lease fakeLease = new Lease();
        User fakeUser = new User();

        doNothing().when(userRepository).verifyLoggedInUser("REPAIR");
        when(userRepository.getcurrentUser()).thenReturn(fakeUser);
        when(leaseRepository.findLeaseById(leaseId)).thenReturn(fakeLease);

        mockMvc.perform(MockMvcRequestBuilders.get("/printDamageReport")
                        .param("leaseId", String.valueOf(leaseId)))
                .andExpect(status().isOk())
                .andExpect(view().name("printDamageReport"))
                .andExpect(model().attribute("leaseId", leaseId));
    }
}
*/