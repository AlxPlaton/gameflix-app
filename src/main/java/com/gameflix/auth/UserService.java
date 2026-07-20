package com.gameflix.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * User Authentication Logic Service
 * This service layer coordinates the processing logic for user accounts. It validates 
 * uniqueness constraints during account creation, triggers the cryptographic password 
 * hashing via BCrypt, and performs verification matches for inbound login credentials.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        String hashedPw = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPw);
        userRepository.save(newUser);
        return true;
    }

    // Check User Auth
    public boolean authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username); // Check Repository for username
        if (userOpt.isEmpty()) {
            return false;
        }
        return passwordEncoder.matches(password, userOpt.get().getPasswordHash()); // return true if password also matches
    }
}