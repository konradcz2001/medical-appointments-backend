package com.github.konradcz2001.medicalappointments.exception;

public class WrongRoleException extends RuntimeException{

    public WrongRoleException() {
        super("Given role is not allowed");
    }
}
