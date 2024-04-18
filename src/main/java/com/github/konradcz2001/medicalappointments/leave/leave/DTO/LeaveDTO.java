package com.github.konradcz2001.medicalappointments.leave.leave.DTO;

import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTO;

import java.time.LocalDateTime;

public record LeaveDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, DoctorDTO doctor){
}
