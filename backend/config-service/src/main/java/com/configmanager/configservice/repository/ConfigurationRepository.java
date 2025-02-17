package com.configmanager.configservice.repository;

import com.configmanager.configservice.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for Configuration entity operations.
 * Provides CRUD operations and custom queries for configurations.
 */
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    
    /**
     * Finds a configuration by its unique key.
     *
     * @param key The configuration key to search for
     * @return Optional containing the configuration if found
     */
    Optional<Configuration> findByKey(String key);

    /**
     * Checks if a configuration with the given key exists.
     *
     * @param key The configuration key to check
     * @return true if configuration exists, false otherwise
     */
    boolean existsByKey(String key);
}