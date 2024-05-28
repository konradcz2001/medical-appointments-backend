package com.github.konradcz2001.medicalappointments.specialization.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a data transfer object for a specialization.
 * <p>
 * This record contains the following fields:
 * - id: An integer representing the ID of the specialization.
 * - specialization: A non-blank string representing the name of the specialization.
 */
public record SpecializationDTO(Integer id,
                                @NotBlank(message = "Specialization must not be empty")
                                @Size(max = 100, message = "Maximum length is 100 characters")
                                String specialization){
}
