package com.github.konradcz2001.medicalappointments.specialization.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpecializationDTO(Integer id,
                                @NotBlank String specialization){
}
