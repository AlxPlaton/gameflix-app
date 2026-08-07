package com.gameflix.auth;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import jakarta.servlet.DispatcherType;


/**
 * Security Configuration Class
 * This class configures the Spring Security filter chain for the GameFlix platform.
 * It disables CSRF protection for stateless API requests, opens up public access
 * to the "/register" and "/login" endpoints, and registers the BCryptPasswordEncoder 
 * bean used to securely salt and hash user passwords.
 */
@Configuration
public class SecurityConfig extends OncePerRequestFilter {

	private final JwtAuthenticationFilter jwtFilter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Must Use BCrypt
    }
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	// Permit /api/auth/** AND root /login, /register
                .requestMatchers("/api/auth/login", "/api/auth/register", "/login", "/register", "/error").permitAll() // Explicitly allow these two paths
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Skip JWT validation for public authentication endpoints
        return path.startsWith("/api/auth/") || path.equals("/login") || path.equals("/register");
    }
}