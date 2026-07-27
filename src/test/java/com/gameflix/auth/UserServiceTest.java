package com.gameflix.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    // 1. Base test: Register a new user successfully
    @Test
    void registerUser_ShouldReturnTrueForNewUser() {
        // Unique username so test runs clean
        String testUser = "testUser_" + System.currentTimeMillis(); 
        boolean registered = userService.registerUser(testUser, "password123");
        Assertions.assertTrue(registered);
    }

    // 2. Additional Test #1: Prevent registering duplicate username
    @Test
    void registerUser_ShouldReturnFalseForDuplicateUser() {
        String testUser = "duplicateUser_" + System.currentTimeMillis();
        // First registration succeeds
        userService.registerUser(testUser, "password123"); 
        // Second registration with same username fails
        boolean secondAttempt = userService.registerUser(testUser, "password123"); 
        Assertions.assertFalse(secondAttempt);
    }

    // 3. Additional Test #2: Authenticate user credentials
    @Test
    void authenticateUser_ShouldReturnTrueForValidCredentials() {
        String testUser = "authUser_" + System.currentTimeMillis();
        userService.registerUser(testUser, "securePass");
        
        boolean authenticated = userService.authenticateUser(testUser, "securePass");
        Assertions.assertTrue(authenticated);
    }
}