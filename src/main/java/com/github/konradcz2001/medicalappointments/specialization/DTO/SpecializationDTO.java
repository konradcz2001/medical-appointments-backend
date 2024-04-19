package com.github.konradcz2001.medicalappointments.specialization.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a data transfer object for a specialization.
 * <p>
 * This record contains the following fields:
 * - id: An integer representing the ID of the specialization.
 * - specialization: A non-blank string representing the name of the specialization.
 */
public record SpecializationDTO(Integer id,
                                @NotBlank String specialization){
}
