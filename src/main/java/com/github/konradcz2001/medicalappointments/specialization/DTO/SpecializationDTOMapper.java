package com.github.konradcz2001.medicalappointments.specialization.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import org.springframework.stereotype.Service;

/**
 * This is a class named SpecializationDTOMapper that implements the DTOMapper interface.
 * It is responsible for mapping Specialization objects to SpecializationDTO objects and vice versa.
 * The class is annotated with @Service, indicating that it is a Spring service component.
 * <p>
 * The class has two methods:
 * - mapToDTO: This method takes a Specialization object as input and returns a SpecializationDTO object.
 *   It maps the id and specialization fields from the Specialization object to the corresponding fields in the SpecializationDTO object.
 * - mapFromDTO: This method takes a SpecializationDTO object and a Specialization object as input and returns the modified Specialization object.
 *   It sets the specialization field of the Specialization object to the value from the SpecializationDTO object.
 * <p>
 * Note: The SpecializationDTOMapper class is used for mapping Specialization objects to SpecializationDTO objects and vice versa.
 */
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
