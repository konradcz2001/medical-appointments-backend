package com.github.konradcz2001.medicalappointments.doctor.leave.DTO;

import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorResponseDTO;

import java.time.LocalDateTime;

public record LeaveResponseDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, DoctorResponseDTO doctor){
}
