package com.github.konradcz2001.medicalappointments.exception.exceptions;

/**
 * Represents a custom exception for wrong visit.
 * <p>
 * This exception is thrown when a wrong visit is encountered.
 * It extends the RuntimeException class.
 * <p>
 * Param message - the error message associated with the exception
 */
public class WrongVisitException extends RuntimeException {
    public WrongVisitException(String message) {
        super(message);
    }
}
