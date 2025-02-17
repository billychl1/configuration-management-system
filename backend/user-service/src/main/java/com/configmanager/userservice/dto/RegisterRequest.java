package com.configmanager.userservice.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * Contains the necessary information to create a new user account.
 */
@Data
public class RegisterRequest {
    /**
     * Username for the new account.
     * Must be unique in the system.
     */
    private String username;

    /**
     * Password for the new account.
     * Will be encrypted before storage.
     */
    private String password;

    /**
     * Email address for the new account.
     * Used for account verification and communication.
     */
    private String email;
}