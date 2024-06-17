package com.github.konradcz2001.medicalappointments.exception.exceptions;


/**
 * Represents a custom exception for wrong leave.
 * <p>
 * This exception is thrown when a wrong leave is encountered.
 * It extends the RuntimeException class.
 * <p>
 * Param message - the error message associated with the exception
 */
public class WrongLeaveException extends RuntimeException {

    public WrongLeaveException(String message) {
        super(message);
    }
}
