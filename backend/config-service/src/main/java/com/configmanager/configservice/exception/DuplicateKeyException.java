package com.configmanager.configservice.exception;

/**
 * Exception thrown when attempting to create a configuration with a key that already exists.
 * This is a runtime exception used in the configuration service to maintain key uniqueness.
 */
public class DuplicateKeyException extends RuntimeException {
    
    /**
     * Constructs a new duplicate key exception with the specified detail message.
     *
     * @param message The detail message explaining the duplicate key violation
     */
    public DuplicateKeyException(String message) {
        super(message);
    }
}