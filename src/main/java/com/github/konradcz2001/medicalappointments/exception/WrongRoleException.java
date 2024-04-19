package com.github.konradcz2001.medicalappointments.exception;

/**
 * Exception class for handling wrong role.
 * This exception is thrown when a given role is not allowed.
 */
public class WrongRoleException extends RuntimeException{

    public WrongRoleException() {
        super("Given role is not allowed");
    }
}
