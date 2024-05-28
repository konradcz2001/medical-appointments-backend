package com.github.konradcz2001.medicalappointments.doctor.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Represents a data transfer object for a doctor's leave.
 * <p>
 * This DTO contains information about a doctor's leave, including the leave ID, start date, and end date.
 */
public record DoctorLeaveDTO(Long id,
                             @NotNull(message = "Start date must not be empty")
                             @FutureOrPresent(message = "Start date must not be in the past")
                             LocalDateTime startDate,
                             @NotNull(message = "End date must not be empty")
                             @FutureOrPresent(message = "End date must not be in the past")
                             LocalDateTime endDate){
}

