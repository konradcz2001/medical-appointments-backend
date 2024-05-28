package com.github.konradcz2001.medicalappointments.exception;

import java.time.LocalDateTime;

/**
 * Represents a record class for storing API error information.
 * <p>
 * This class provides a convenient way to encapsulate the details of an API error, including the path, message, status, and local date and time.
 * It is designed to be used as an immutable data holder, with all fields being final and set through the constructor.
 *
 * @param name The error name.
 * @param path The path associated with the error.
 * @param message The error message.
 * @param status The HTTP status code of the error.
 * @param localDateTime The local date and time when the error occurred.
 */
public record ApiError( String name,
                        String path,
                        String message,
                        int status,
                        LocalDateTime localDateTime) {
}
