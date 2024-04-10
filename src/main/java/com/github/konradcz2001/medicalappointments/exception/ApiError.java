package com.github.konradcz2001.medicalappointments.exception;

import java.time.LocalDateTime;

public record ApiError(String path, String message, int status, LocalDateTime localDateTime) {
}
