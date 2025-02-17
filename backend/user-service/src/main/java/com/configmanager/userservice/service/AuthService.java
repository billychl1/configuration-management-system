package com.configmanager.userservice.service;

import com.configmanager.usermodule.model.User;
import com.configmanager.usermodule.repository.UserRepository;
import com.configmanager.usermodule.security.JwtTokenUtil;
import com.configmanager.userservice.dto.AuthRequest;
import com.configmanager.userservice.dto.AuthResponse;
import com.configmanager.userservice.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authentication operations.
 * Handles user registration and login with JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Repository for user entity operations.
     */
    private final UserRepository userRepository;

    /**
     * Encoder for password hashing.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Utility for JWT token operations.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Manager for authentication processes.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user in the system.
     * Checks for existing username, creates new user with encoded password,
     * and generates JWT token.
     *
     * @param request Registration details containing username and password
     * @return AuthResponse with JWT token
     * @throws RuntimeException if username already exists
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER"); // Default role

        // Save user to database
        userRepository.save(user);

        // Generate JWT token
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();

        String token = jwtTokenUtil.generateToken(userDetails);

        // Create and return response
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    /**
     * Authenticates user credentials and generates JWT token.
     * Validates username/password combination and creates authentication token.
     *
     * @param request Login credentials containing username and password
     * @return AuthResponse with JWT token
     * @throws RuntimeException if authentication fails
     */
    public AuthResponse login(AuthRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token
            String token = jwtTokenUtil.generateToken(userDetails);

            // Create and return response
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Invalid username/password");
        }
    }
}