package com.github.konradcz2001.medicalappointments.specialization.specialization.DTO;

import jakarta.validation.constraints.NotNull;

public record SpecializationDTO(Integer id, @NotNull String specialization){
}
