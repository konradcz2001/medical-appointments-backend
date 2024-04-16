package com.github.konradcz2001.medicalappointments.visit.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.visit.Visit;
import org.springframework.stereotype.Service;

@Service
public class VisitDTOMapper implements DTOMapper<VisitResponseDTO, Visit> {

    @Override
    public VisitResponseDTO apply(Visit visit) {
        return new VisitResponseDTO(
                visit.getId(),
                visit.getDate(),
                visit.getType(),
                visit.getNotes(),
                visit.getPrice(),
                visit.getDoctor().getId(),
                visit.getClient().getId()
        );
    }


}
