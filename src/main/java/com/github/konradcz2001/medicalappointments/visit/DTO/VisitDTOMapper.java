package com.github.konradcz2001.medicalappointments.visit.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.visit.Visit;
import org.springframework.stereotype.Service;

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
