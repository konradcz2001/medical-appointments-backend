package com.github.konradcz2001.medicalappointments.exception.exceptions;


/**
 * Represents a custom exception for wrong type of visit.
 * <p>
 * This exception is thrown when a wrong type of visit is encountered.
 * It extends the RuntimeException class.
 * <p>
 * Param message - the error message associated with the exception
 */
public class WrongTypeOfVisitException extends RuntimeException {

    public WrongTypeOfVisitException(String message) {
        super(message);
    }
}
