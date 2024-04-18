package com.github.konradcz2001.medicalappointments.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class WrongLeaveException extends RuntimeException {

    public WrongLeaveException(String message) {
        super(message);
    }
}
