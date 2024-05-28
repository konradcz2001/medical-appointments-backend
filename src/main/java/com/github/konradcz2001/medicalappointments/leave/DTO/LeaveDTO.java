package com.github.konradcz2001.medicalappointments.leave.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Represents a data transfer object for Leave information.
 * <p>
 * This class is used to transfer Leave data between different layers of the application.
 * It contains the following properties:
 * - id: The unique identifier for the Leave.
 * - startDate: The start date of the Leave.
 * - endDate: The end date of the Leave.
 * - doctorId: The unique identifier of the doctor associated with the Leave.
 */
public record LeaveDTO(Long id,
                       @NotNull(message = "Start date must not be empty")
                       @FutureOrPresent(message = "Start date must not be in the past")
                       LocalDateTime startDate,
                       @NotNull(message = "End date must not be empty")
                       @FutureOrPresent(message = "End date must not be in the past")
                       LocalDateTime endDate,
                       @NotNull(message = "Doctor id must not be empty")
                       Long doctorId){
}
