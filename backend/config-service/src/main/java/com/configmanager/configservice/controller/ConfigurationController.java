package com.configmanager.configservice.controller;

import com.configmanager.configservice.model.Configuration;
import com.configmanager.configservice.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing configurations.
 * Provides CRUD operations for configuration management.
 * All endpoints are under /api/configs base path.
 */
@RestController
@RequestMapping("/api/configs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConfigurationController {

    /**
     * Service layer for configuration operations.
     */
    private final ConfigurationService configurationService;

    /**
     * Retrieves all configurations from the system.
     *
     * @return List of all configurations
     */
    @GetMapping
    @Operation(summary = "Get all configurations")
    public ResponseEntity<List<Configuration>> getAllConfigurations() {
        return ResponseEntity.ok(configurationService.getAllConfigurations());
    }

    /**
     * Retrieves a specific configuration by its ID.
     *
     * @param id The configuration ID
     * @return The requested configuration
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get configuration by ID")
    public ResponseEntity<Configuration> getConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(configurationService.getConfigurationById(id));
    }

    /**
     * Retrieves a specific configuration by its key.
     *
     * @param key The configuration key
     * @return The requested configuration
     */
    @GetMapping("/key/{key}")
    @Operation(summary = "Get configuration by key")
    public ResponseEntity<Configuration> getConfigurationByKey(@PathVariable String key) {
        return ResponseEntity.ok(configurationService.getConfigurationByKey(key));
    }

    /**
     * Creates a new configuration.
     *
     * @param configuration The configuration to create
     * @param authentication The current authenticated user
     * @return The created configuration
     */
    @PostMapping
    @Operation(summary = "Create new configuration")
    public ResponseEntity<Configuration> createConfiguration(
            @Valid @RequestBody Configuration configuration,
            Authentication authentication) {
        return ResponseEntity.ok(
            configurationService.createConfiguration(configuration, authentication.getName())
        );
    }

    /**
     * Updates an existing configuration.
     *
     * @param id The configuration ID to update
     * @param configuration The new configuration data
     * @param authentication The current authenticated user
     * @return The updated configuration
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update existing configuration")
    public ResponseEntity<Configuration> updateConfiguration(
            @PathVariable Long id,
            @Valid @RequestBody Configuration configuration,
            Authentication authentication) {
        return ResponseEntity.ok(
            configurationService.updateConfiguration(id, configuration, authentication.getName())
        );
    }

    /**
     * Deletes a configuration by its ID.
     *
     * @param id The configuration ID to delete
     * @return No content response
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete configuration")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}