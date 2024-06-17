package com.github.konradcz2001.medicalappointments.exception.exceptions;



public class WrongScheduleException extends RuntimeException {

    public WrongScheduleException(String message) {
        super(message + " start time can not be after end time");
    }
}
