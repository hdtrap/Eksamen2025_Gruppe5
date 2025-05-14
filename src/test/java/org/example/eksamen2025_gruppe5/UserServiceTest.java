package org.example.eksamen2025_gruppe5;

import org.example.eksamen2025_gruppe5.model.User;
import org.example.eksamen2025_gruppe5.repository.UserRepository;
import org.example.eksamen2025_gruppe5.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    DataSource dataSource;

    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception{
        userRepository = new UserRepository(dataSource);
    }

    @Test
    void test_isUserNameUserXXXX() throws Exception{
        //Arrange
        String username = "user0001";

        //Act
        Boolean result= userRepository.isUsernameUserXXXX(username);

        //Assert
        assertTrue(result);
    }
}
