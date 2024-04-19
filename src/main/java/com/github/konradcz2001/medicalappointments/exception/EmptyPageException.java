package com.github.konradcz2001.medicalappointments.exception;

/**
 * Represents a custom exception that is thrown when a page is empty.
 */
public class EmptyPageException extends RuntimeException{
    public EmptyPageException() {
        super("Page is empty");
    }
}
