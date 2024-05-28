package com.github.konradcz2001.medicalappointments.doctor.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a data transfer object for a doctor's specialization.
 * <p>
 *  id: the unique identifier of the specialization,
 *  specialization: the name of the specialization
 */
public record DoctorSpecializationDTO(Integer id,
                                      @NotBlank(message = "Specialization must not be empty")
                                      @Size(max = 100, message = "Maximum length is 100 characters")
                                      String specialization){
}

