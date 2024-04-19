package com.github.konradcz2001.medicalappointments.visit.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.visit.Visit;
import org.springframework.stereotype.Service;

/**
 * This is a Java class named "VisitDTOMapper" that implements the "DTOMapper" interface.
 * It provides methods to map a "Visit" object to a "VisitDTO" object and vice versa.
 * The "mapToDTO" method takes a "Visit" object as input and returns a new "VisitDTO" object with the corresponding properties.
 * The "mapFromDTO" method takes a "VisitDTO" object and a target "Visit" object as input, and sets the properties of the target object based on the values in the DTO object.
 * This class is annotated with the "@Service" annotation to indicate that it is a Spring service component.
 */
@Service
public class VisitDTOMapper implements DTOMapper<VisitDTO, Visit> {

    @Override
    public VisitDTO mapToDTO(Visit source) {
        return new VisitDTO(
                source.getId(),
                source.getDate(),
                source.getType(),
                source.getNotes(),
                source.getPrice(),
                source.getDoctor().getId(),
                source.getClient().getId()
        );
    }

    @Override
    public Visit mapFromDTO(VisitDTO sourceDTO, Visit target) {
        target.setDate(sourceDTO.date());
        target.setType(sourceDTO.type());
        target.setNotes(sourceDTO.notes());
        target.setPrice(sourceDTO.price());
        return target;
    }


}
