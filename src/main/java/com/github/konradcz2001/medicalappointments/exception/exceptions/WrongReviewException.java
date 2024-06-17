package com.github.konradcz2001.medicalappointments.exception.exceptions;


/**
 * Represents a custom exception for wrong reviews.
 * <p>
 * This exception is thrown when a wrong review is encountered.
 * It extends the RuntimeException class.
 * <p>
 * Param message - the error message associated with the exception
 */
public class WrongReviewException extends RuntimeException {

    public WrongReviewException(String message) {
        super(message);
    }
}
