package com.github.konradcz2001.medicalappointments.visit.DTO;


import com.github.konradcz2001.medicalappointments.visit.Visit;

public class VisitDTOMapper {

    public static VisitResponseDTO apply(Visit visit) {
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
