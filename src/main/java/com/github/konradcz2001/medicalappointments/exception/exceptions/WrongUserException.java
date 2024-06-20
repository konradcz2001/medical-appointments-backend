package com.github.konradcz2001.medicalappointments.exception.exceptions;


import org.springframework.security.core.AuthenticationException;

/**
 * Exception class for handling wrong User.
 * This exception is thrown when a given User does not exist.
 */
public class WrongUserException extends AuthenticationException {

    public WrongUserException() {
        super("User does not exist");
    }
}
