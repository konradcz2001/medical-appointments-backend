package com.github.konradcz2001.medicalappointments.specialization.specialization.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.specialization.specialization.Specialization;
import org.springframework.stereotype.Service;

@Service
public class SpecializationDTOMapper implements DTOMapper<SpecializationResponseDTO, Specialization> {

    @Override
    public SpecializationResponseDTO apply(Specialization specialization) {
        return new SpecializationResponseDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

}
