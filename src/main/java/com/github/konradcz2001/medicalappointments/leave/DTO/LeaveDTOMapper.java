package com.github.konradcz2001.medicalappointments.leave.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import org.springframework.stereotype.Service;

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
