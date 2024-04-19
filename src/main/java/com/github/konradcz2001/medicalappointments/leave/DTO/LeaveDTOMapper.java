package com.github.konradcz2001.medicalappointments.leave.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import org.springframework.stereotype.Service;

/**
 * This is a Java class named "LeaveDTOMapper" that implements the "DTOMapper" interface.
 * It is a service class responsible for mapping a "Leave" object to a "LeaveDTO" object and vice versa.
 * The class provides two methods:
 * - mapToDTO: This method takes a "Leave" object as input and returns a "LeaveDTO" object with the mapped data.
 * - mapFromDTO: This method takes a "LeaveDTO" object and a target "Leave" object as input and maps the data from the DTO to the target object.
 * <p>
 * Note: The class is annotated with "@Service" to indicate that it is a Spring service component.
 */
@Service
public class LeaveDTOMapper implements DTOMapper<LeaveDTO, Leave> {


    @Override
    public LeaveDTO mapToDTO(Leave source) {
        return new LeaveDTO(
                source.getId(),
                source.getStartDate(),
                source.getEndDate(),
                source.getDoctor().getId()
        );
    }

    @Override
    public Leave mapFromDTO(LeaveDTO sourceDTO, Leave target) {
        target.setStartDate(sourceDTO.startDate());
        target.setEndDate(sourceDTO.endDate());

        return target;
    }

}
