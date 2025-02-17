package com.configmanager.usermodule.repository;

import com.configmanager.usermodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides CRUD operations and custom queries for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their username.
     *
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
}