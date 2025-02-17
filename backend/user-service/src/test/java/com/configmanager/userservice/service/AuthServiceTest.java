package com.configmanager.userservice.service;

import com.configmanager.usermodule.model.User;
import com.configmanager.usermodule.repository.UserRepository;
import com.configmanager.usermodule.security.JwtTokenUtil;
import com.configmanager.userservice.dto.AuthRequest;
import com.configmanager.userservice.dto.AuthResponse;
import com.configmanager.userservice.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService class.
 * Tests authentication and registration functionality using mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, passwordEncoder, jwtTokenUtil, authenticationManager);
    }

    @Test
    void register_WhenUsernameDoesNotExist_ShouldRegisterSuccessfully() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("jwt.token.here");

        // Act
        AuthResponse response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("jwt.token.here", response.getToken());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }

    @Test
    void register_WhenUsernameExists_ShouldThrowException() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");
        request.setPassword("password123");

        when(userRepository.findByUsername("existinguser"))
                .thenReturn(Optional.of(new User()));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_WhenCredentialsAreValid_ShouldLoginSuccessfully() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("encodedPassword")
                .authorities("ROLE_USER")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("jwt.token.here");

        // Act
        AuthResponse response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("jwt.token.here", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_WhenCredentialsAreInvalid_ShouldThrowException() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
        assertEquals("Invalid username/password", exception.getMessage());
    }
}