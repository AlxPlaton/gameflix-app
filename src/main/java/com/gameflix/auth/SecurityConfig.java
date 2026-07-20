package com.gameflix.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;


/**
 * Security Configuration Class
 * This class configures the Spring Security filter chain for the GameFlix platform.
 * It disables CSRF protection for stateless API requests, opens up public access
 * to the "/register" and "/login" endpoints, and registers the BCryptPasswordEncoder 
 * bean used to securely salt and hash user passwords.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Must Use BCrypt
    }
    


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow internal dispatches (e.g. when Spring forwards an exception to /error)
                .dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll()
                // Explicitly permit public endpoints, actuator, and the error handler
                .requestMatchers("/register", "/login", "/actuator", "/actuator/**", "/error").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}