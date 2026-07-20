package com.gameflix.auth;

import jakarta.persistence.*;

/**
 * User Entity Model
 * This class serves as the Persistent Data Model mapping directly to the
 * "users" database table. It defines the schema structure including the primary key (id), 
 * a unique username attribute, and the securely encrypted password string.
 */
@Entity
@Table(name = "users")
public class User {
	
	// Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    public User() {} // Keep to prevent exception | db interaction
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Simple methods
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}