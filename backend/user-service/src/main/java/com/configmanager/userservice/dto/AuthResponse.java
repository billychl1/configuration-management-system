package com.configmanager.userservice.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication responses.
 * Contains the JWT token returned after successful authentication.
 */
@Data
public class AuthResponse {
    /**
     * JWT token for authenticated requests.
     * Used in Authorization header with Bearer scheme.
     */
    private String token;
}