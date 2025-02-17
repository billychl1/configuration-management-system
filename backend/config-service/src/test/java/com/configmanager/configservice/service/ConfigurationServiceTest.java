package com.configmanager.configservice.service;

import com.configmanager.configservice.model.Configuration;
import com.configmanager.configservice.repository.ConfigurationRepository;
import com.configmanager.configservice.exception.ConfigurationNotFoundException;
import com.configmanager.configservice.exception.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ConfigurationService.
 * Tests CRUD operations and business logic validation.
 */
@ExtendWith(MockitoExtension.class)
public class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationService configurationService;

    private Configuration testConfiguration;
    private final String TEST_USERNAME = "testUser";

    @BeforeEach
    void setUp() {
        testConfiguration = new Configuration();
        testConfiguration.setId(1L);
        testConfiguration.setKey("test.key");
        testConfiguration.setValue("test-value");
        testConfiguration.setDescription("Test configuration");
    }

    @Test
    void getAllConfigurations_ShouldReturnList() {
        // Arrange
        when(configurationRepository.findAll())
            .thenReturn(Arrays.asList(testConfiguration));

        // Act
        List<Configuration> result = configurationService.getAllConfigurations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testConfiguration.getKey(), result.get(0).getKey());
    }

    @Test
    void getConfigurationById_WhenExists_ShouldReturnConfiguration() {
        // Arrange
        when(configurationRepository.findById(1L))
            .thenReturn(Optional.of(testConfiguration));

        // Act
        Configuration result = configurationService.getConfigurationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testConfiguration.getKey(), result.getKey());
    }

    @Test
    void getConfigurationById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(configurationRepository.findById(1L))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ConfigurationNotFoundException.class,
            () -> configurationService.getConfigurationById(1L));
    }

    @Test
    void createConfiguration_WhenKeyNotExists_ShouldCreateConfiguration() {
        // Arrange
        when(configurationRepository.existsByKey(testConfiguration.getKey()))
            .thenReturn(false);
        when(configurationRepository.save(any(Configuration.class)))
            .thenReturn(testConfiguration);

        // Act
        Configuration result = configurationService.createConfiguration(testConfiguration, TEST_USERNAME);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_USERNAME, result.getCreatedBy());
        assertEquals(TEST_USERNAME, result.getLastModifiedBy());
        verify(configurationRepository).save(any(Configuration.class));
    }

    @Test
    void createConfiguration_WhenKeyExists_ShouldThrowException() {
        // Arrange
        when(configurationRepository.existsByKey(testConfiguration.getKey()))
            .thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateKeyException.class,
            () -> configurationService.createConfiguration(testConfiguration, TEST_USERNAME));
        verify(configurationRepository, never()).save(any(Configuration.class));
    }

    @Test
    void updateConfiguration_WhenExists_ShouldUpdateConfiguration() {
        // Arrange
        Configuration updatedConfig = new Configuration();
        updatedConfig.setKey("test.key");
        updatedConfig.setValue("updated-value");
        updatedConfig.setDescription("Updated description");

        when(configurationRepository.findById(1L))
            .thenReturn(Optional.of(testConfiguration));
        when(configurationRepository.save(any(Configuration.class)))
            .thenReturn(updatedConfig);

        // Act
        Configuration result = configurationService.updateConfiguration(1L, updatedConfig, TEST_USERNAME);

        // Assert
        assertNotNull(result);
        assertEquals(updatedConfig.getValue(), result.getValue());
        verify(configurationRepository).save(any(Configuration.class));
    }

    @Test
    void deleteConfiguration_WhenExists_ShouldDelete() {
        // Arrange
        when(configurationRepository.existsById(1L)).thenReturn(true);

        // Act
        configurationService.deleteConfiguration(1L);

        // Assert
        verify(configurationRepository).deleteById(1L);
    }

    @Test
    void deleteConfiguration_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(configurationRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ConfigurationNotFoundException.class,
            () -> configurationService.deleteConfiguration(1L));
        verify(configurationRepository, never()).deleteById(any());
    }
}