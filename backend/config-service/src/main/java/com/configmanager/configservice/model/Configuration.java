package com.configmanager.configservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a configuration in the system.
 * Stores key-value pairs with metadata like creation and modification timestamps.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configurations")
public class Configuration {
    
    /**
     * Unique identifier for the configuration.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique key for the configuration.
     * Must not be blank and must be unique across all configurations.
     */
    @NotBlank(message = "Key is required")
    @Column(unique = true, name = "`key`", nullable = false)
    private String key;

    /**
     * Value associated with the configuration key.
     * Must not be null.
     */
    @Column(nullable = false)
    private String value;

    /**
     * Optional description of the configuration's purpose.
     */
    private String description;

    /**
     * Username of the user who created the configuration.
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * Username of the user who last modified the configuration.
     */
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /**
     * Timestamp when the configuration was created.
     */
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    /**
     * Timestamp when the configuration was last updated.
     */
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    /**
     * Sets creation and update timestamps when persisting.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }

    /**
     * Updates the modification timestamp when updating.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}