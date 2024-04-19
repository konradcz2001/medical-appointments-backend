package com.github.konradcz2001.medicalappointments.doctor.DTO;

/**
 * Represents a data transfer object for a doctor's specialization.
 * <p>
 *  id: the unique identifier of the specialization,
 *  specialization: the name of the specialization
 */
public record DoctorSpecializationDTO(Integer id,
                                      String specialization){
}

