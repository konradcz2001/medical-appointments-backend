package com.github.konradcz2001.medicalappointments.doctor.DTO;

import java.time.LocalDateTime;

public record DoctorLeaveDTO(Long id, LocalDateTime startDate, LocalDateTime endDate){
}

