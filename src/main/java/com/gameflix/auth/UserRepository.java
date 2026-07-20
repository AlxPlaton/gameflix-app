package com.gameflix.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * User Data Access Repository Interface
 * This interface abstracts the database layer by extending JpaRepository. It autogenerates 
 * SQL CRUD mechanisms and provides targeted custom database lookup methods 
 * to handle authentication verification and check for existing duplicate usernames.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}