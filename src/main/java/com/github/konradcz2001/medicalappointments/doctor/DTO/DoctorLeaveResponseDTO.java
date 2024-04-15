package com.github.konradcz2001.medicalappointments.doctor.DTO;

import java.time.LocalDateTime;

public record DoctorLeaveResponseDTO(Long id, LocalDateTime startDate, LocalDateTime endDate){
}

