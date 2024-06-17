package com.github.konradcz2001.medicalappointments.exception.exceptions;


/**
 * The WrongScheduleException class represents an exception that is thrown when an invalid schedule is encountered.
 * It extends the RuntimeException class.
 * <p>
 * This exception is typically thrown when the start time is after the end time for a specific day, or when the start date
 * and end date are not both set or neither is set, as one cannot be set without the other.
 * <p>
 * The class provides methods to choose the appropriate error message based on the day and number provided.
 * The day is represented as an integer from 0 to 6, where 0 is Monday and 6 is Sunday.
 *
 */
public class WrongScheduleException extends RuntimeException {

    public WrongScheduleException(int day, boolean startAfterEnd) {
        super(chooseMessageType(day, startAfterEnd));
    }

    private static String chooseMessageType(int day , boolean startAfterEnd){

        if(startAfterEnd)
            return chooseDay(day) + " start time can not be after end time";
        else
            return chooseDay(day) + " start date and end date must both be set or neither, one cannot be set without the other";
    }

    private static String chooseDay(int day){

        return switch (day) {
            case 0 -> "Monday";
            case 1 -> "Tuesday";
            case 2 -> "Wednesday";
            case 3 -> "Thursday";
            case 4 -> "Friday";
            case 5 -> "Saturday";
            case 6 -> "Sunday";
            default -> "Unknown day";
        };

    }
}
