package org.example.eksamen2025_gruppe5;

import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.example.eksamen2025_gruppe5.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;


    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception{
        userRepository = new UserRepository(dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void test_findNumberForUsername_FindsMatch_ReturnsTheMatch() throws Exception{
        //Arrange
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(anyString())).thenReturn("mimi0001", "mimi0002");

        //Act
        String result = userRepository.findNumberForUsername("mimi");

        //Assert
        assertEquals("0003", result);
    }
}