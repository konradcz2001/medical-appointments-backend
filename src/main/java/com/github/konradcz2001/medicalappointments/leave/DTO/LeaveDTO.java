package com.github.konradcz2001.medicalappointments.leave.DTO;

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
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       Long doctorId){
}
