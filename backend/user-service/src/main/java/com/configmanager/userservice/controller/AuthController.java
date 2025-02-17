package com.configmanager.userservice.controller;

import com.configmanager.userservice.dto.AuthRequest;
import com.configmanager.userservice.dto.AuthResponse;
import com.configmanager.userservice.dto.RegisterRequest;
import com.configmanager.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for handling authentication operations.
 * Provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Authentication management API")
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user registration requests.
     * Creates a new user account and returns authentication token.
     *
     * @param request Registration details including username and password
     * @return ResponseEntity containing authentication token and user details
     */
    @Operation(summary = "Register a new user", 
              description = "Creates a new user account with the provided credentials")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Handles user login requests.
     * Authenticates user credentials and returns authentication token.
     *
     * @param request Login credentials including username and password
     * @return ResponseEntity containing authentication token and user details
     */
    @Operation(summary = "Authenticate user", 
              description = "Validates credentials and returns authentication token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}