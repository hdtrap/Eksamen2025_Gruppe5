package org.example.eksamen2025_gruppe5;

import org.example.eksamen2025_gruppe5.model.Car;
import org.example.eksamen2025_gruppe5.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class CarRepository_UnitTest_makeCarStatusAvailable {

    // Frederiks unit Test
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks //InjectMock bruges når det er den klasse man tester
    private CarRepository carRepository;

    @Mock
    private Car car;

    @BeforeEach
    public void setUp() {
        // Initialiser og nulstiller mockeobjekterne før hver test, man kan slippe for dette hvis man bruger @ExtendWith(MockitoExtension.class) annotation i klassen, men det gør jeg ikke
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMakeCarStatusAvailable_HappyFlow() throws SQLException {
        // Assumptions
        int vehicleNumber = 42; // Eksempel på et vehicleNumber
        when(car.getVehicleNumber()).thenReturn(vehicleNumber); // Simuler, at car.getVehicleNumber() returnerer 42
        when(dataSource.getConnection()).thenReturn(connection); // Simuler, at vi får en Connection
        when(connection.prepareStatement("UPDATE cars SET status_of_car = ? WHERE vehicle_no = ?")).thenReturn(preparedStatement); // Simuler SQL-forberedelse

        // Execution
        carRepository.makeCarStatusAvailable(car);

        // Validation
        
        verify(connection).prepareStatement("UPDATE cars SET status_of_car = ? WHERE vehicle_no = ?");// Tjekker at vi kalder prepareStatement med den rigtige SQL prompt
        verify(preparedStatement).setString(1, "AvailableToLease");// Tjekker at vi satte "AvailableToLease" som første parameter
        verify(preparedStatement).setInt(2, vehicleNumber);// Tjekker at vi satte vehicleNumber som anden parameter
        verify(preparedStatement).executeUpdate();// Tjekker at vi kaldte executeUpdate for at opdatere databasen
        verify(connection).close();// Tjek, at vi lukkede ressourcerne inde i try-catch blokken både connection og preparedStatement
        verify(preparedStatement).close();
    }

    @Test
    public void testMakeCarStatusAvailable_ExceptionFlow() throws SQLException {
        // Assumptions
        int vehicleNumber = 42;
        when(car.getVehicleNumber()).thenReturn(vehicleNumber); // Simuler, at car.getVehicleNumber() returnerer 42
        when(dataSource.getConnection()).thenThrow(new SQLException("Simuleret databasefejl")); // Simuler en databasefejl

        // Execution
        carRepository.makeCarStatusAvailable(car);

        // Validation

        verify(dataSource).getConnection(); // Tjekker at vi forsøgte at få en forbindelse
        verifyNoInteractions(preparedStatement); // Tjekker at vi ikke kom længere pga fejlen
    }
}
