package com.configmanager.configservice.exception;

/**
 * Exception thrown when a requested configuration is not found.
 * This is a runtime exception used in the configuration service
 * to indicate that a configuration lookup operation failed.
 */
public class ConfigurationNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new configuration not found exception with the specified detail message.
     *
     * @param message The detail message explaining why the configuration was not found
     */
    public ConfigurationNotFoundException(String message) {
        super(message);
    }
}