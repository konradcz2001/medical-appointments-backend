package com.github.konradcz2001.medicalappointments.exception.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception class for handling duplicate email.
 * This exception is thrown when a given email already exists.
 */
public class DuplicateEmailException extends AuthenticationException {

    public DuplicateEmailException(String message) {
        super(message + " with given email already exists");
    }
}
