package com.github.konradcz2001.medicalappointments.leave.DTO;

import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LeaveDTO(Long id,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       Long doctorId){
}
