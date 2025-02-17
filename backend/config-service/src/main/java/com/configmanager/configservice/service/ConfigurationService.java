package com.configmanager.configservice.service;

import com.configmanager.configservice.model.Configuration;
import com.configmanager.configservice.repository.ConfigurationRepository;
import com.configmanager.configservice.exception.ConfigurationNotFoundException;
import com.configmanager.configservice.exception.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service layer for managing configuration entities.
 * Provides business logic for CRUD operations on configurations.
 */
@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    /**
     * Retrieves all configurations from the system.
     *
     * @return List of all configurations
     */
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    /**
     * Retrieves a configuration by its ID.
     *
     * @param id The ID of the configuration to retrieve
     * @return The requested configuration
     * @throws ConfigurationNotFoundException if configuration not found
     */
    public Configuration getConfigurationById(Long id) {
        return configurationRepository.findById(id)
            .orElseThrow(() -> new ConfigurationNotFoundException("Configuration not found with id: " + id));
    }

    /**
     * Retrieves a configuration by its key.
     *
     * @param key The key of the configuration to retrieve
     * @return The requested configuration
     * @throws ConfigurationNotFoundException if configuration not found
     */
    public Configuration getConfigurationByKey(String key) {
        return configurationRepository.findByKey(key)
            .orElseThrow(() -> new ConfigurationNotFoundException("Configuration not found with key: " + key));
    }

    /**
     * Creates a new configuration.
     *
     * @param configuration The configuration to create
     * @param username The username of the creator
     * @return The created configuration
     * @throws DuplicateKeyException if the key already exists
     */
    @Transactional
    public Configuration createConfiguration(Configuration configuration, String username) {
        if (configurationRepository.existsByKey(configuration.getKey())) {
            throw new DuplicateKeyException("Configuration with key '" + configuration.getKey() + "' already exists");
        }
        configuration.setCreatedBy(username);
        configuration.setLastModifiedBy(username);
        return configurationRepository.save(configuration);
    }

    /**
     * Updates an existing configuration.
     *
     * @param id The ID of the configuration to update
     * @param configuration The new configuration data
     * @param username The username of the modifier
     * @return The updated configuration
     * @throws ConfigurationNotFoundException if configuration not found
     * @throws DuplicateKeyException if the new key already exists
     */
    @Transactional
    public Configuration updateConfiguration(Long id, Configuration configuration, String username) {
        Configuration existingConfig = getConfigurationById(id);
        
        if (!existingConfig.getKey().equals(configuration.getKey()) 
            && configurationRepository.existsByKey(configuration.getKey())) {
            throw new DuplicateKeyException("Configuration with key '" + configuration.getKey() + "' already exists");
        }

        existingConfig.setKey(configuration.getKey());
        existingConfig.setValue(configuration.getValue());
        existingConfig.setDescription(configuration.getDescription());
        existingConfig.setLastModifiedBy(username);
        
        return configurationRepository.save(existingConfig);
    }

    /**
     * Deletes a configuration by its ID.
     *
     * @param id The ID of the configuration to delete
     * @throws ConfigurationNotFoundException if configuration not found
     */
    @Transactional
    public void deleteConfiguration(Long id) {
        if (!configurationRepository.existsById(id)) {
            throw new ConfigurationNotFoundException("Configuration not found with id: " + id);
        }
        configurationRepository.deleteById(id);
    }
}