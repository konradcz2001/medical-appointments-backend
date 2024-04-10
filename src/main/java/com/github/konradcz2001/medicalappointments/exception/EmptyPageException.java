package com.github.konradcz2001.medicalappointments.exception;

public class EmptyPageException extends RuntimeException{
    public EmptyPageException() {
        super("Page is empty");
    }
}
