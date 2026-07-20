package com.gameflix.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Authentication REST Web Controller
 * This controller exposes the public-facing HTTP API contracts requested for GameFlix. 
 * It listens for incoming POST traffic at "/register" and "/login", handles JSON request 
 * payloads, validates base inputs, and coordinates appropriate HTTP status response codes.
 */
@RestController
public class AuthController {

	// Variables
    @Autowired
    private UserService userService;

    @PostMapping("/register") // Regitser Service Logic
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password"); // temp vars set to inbound JSON txt

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid input data")); // Fail condition based off entry
        }

        boolean success = userService.registerUser(username, password); // Valid User & Pass
        if (success) {
            return ResponseEntity.ok(Map.of("message", "User registered successfully")); // Success, registered in the repo
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists")); // Exists in the repo
        }
    }

    @PostMapping("/login") // Login Service Logic
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password"); // temp vars ^

        boolean authenticated = userService.authenticateUser(username, password); // send to authenticateUser Method
        if (authenticated) {
            return ResponseEntity.ok(Map.of("message", "Login successful")); // Send through to PostMan, Success!
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password")); // Fail Condition
        }
    }
}