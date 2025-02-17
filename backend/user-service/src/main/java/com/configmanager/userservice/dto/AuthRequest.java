package com.configmanager.userservice.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication requests.
 * Contains credentials needed for user authentication.
 */
@Data
public class AuthRequest {
    /**
     * Username for authentication.
     * Must not be null or empty.
     */
    private String username;

    /**
     * Password for authentication.
     * Must not be null or empty.
     * Will be validated against stored hashed password.
     */
    private String password;
}