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
@RequestMapping("/api/auth")
public class AuthController {

	// Variables
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register") // Full route: /api/auth/register
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid input data"));
        }

        boolean success = userService.registerUser(username, password);
        if (success) {
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        }
    }

    @PostMapping("/login") // Full route: /api/auth/login
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                "token", token, // Shows generated JWT 
                "message", "Login successful"
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }
    }
    
    @GetMapping("/test-protected")
    public ResponseEntity<?> testProtected() {
        return ResponseEntity.ok(Map.of("message", "Access granted! Token is valid."));
    }
}