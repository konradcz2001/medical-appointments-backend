package com.github.konradcz2001.medicalappointments.specialization.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import org.springframework.stereotype.Service;

@Service
public class SpecializationDTOMapper implements DTOMapper<SpecializationDTO, Specialization> {

    @Override
    public SpecializationDTO mapToDTO(Specialization source) {
        return new SpecializationDTO(
                source.getId(),
                source.getSpecialization()
        );
    }

    @Override
    public Specialization mapFromDTO(SpecializationDTO sourceDTO, Specialization target) {
        target.setSpecialization(sourceDTO.specialization());
        return target;
    }

}
