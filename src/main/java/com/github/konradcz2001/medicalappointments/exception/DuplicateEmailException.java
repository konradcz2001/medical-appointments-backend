package com.github.konradcz2001.medicalappointments.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Exception class for handling duplicate email.
 * This exception is thrown when a given email already exists.
 */
public class DuplicateEmailException extends BadCredentialsException {

    public DuplicateEmailException(String message) {
        super(message + " with given email already exists");
    }
}
