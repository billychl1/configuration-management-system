package com.configmanager.usermodule.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * User entity representing a user in the system.
 * This class is used for user authentication and authorization.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for authentication.
     * Cannot be null and must be unique across all users.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Encrypted password for user authentication.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * User's role for authorization purposes.
     * Cannot be null.
     * Example values: "ADMIN", "USER"
     */
    @Column(nullable = false)
    private String role;
}