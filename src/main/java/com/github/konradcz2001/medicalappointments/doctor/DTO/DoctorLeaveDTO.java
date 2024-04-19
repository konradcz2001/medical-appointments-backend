package com.github.konradcz2001.medicalappointments.doctor.DTO;

import java.time.LocalDateTime;

/**
 * Represents a data transfer object for a doctor's leave.
 * <p>
 * This DTO contains information about a doctor's leave, including the leave ID, start date, and end date.
 */
public record DoctorLeaveDTO(Long id,
                             LocalDateTime startDate,
                             LocalDateTime endDate){
}

