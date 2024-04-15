package com.github.konradcz2001.medicalappointments.doctor.specialization.DTO;


import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;

public class SpecializationDTOMapper {

    public static SpecializationResponseDTO apply(Specialization specialization) {
        return new SpecializationResponseDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

}
