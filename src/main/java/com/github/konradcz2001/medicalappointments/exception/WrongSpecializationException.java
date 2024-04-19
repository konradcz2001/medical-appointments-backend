package com.github.konradcz2001.medicalappointments.exception;

/**
 * Represents a custom exception for wrong specialization.
 * <p>
 * This exception is thrown when a wrong specialization is encountered.
 * It extends the RuntimeException class.
 * <p>
 * Param message - the error message associated with the exception
 */
public class WrongSpecializationException extends RuntimeException {
    public WrongSpecializationException(String message) {
        super(message);
    }
}
