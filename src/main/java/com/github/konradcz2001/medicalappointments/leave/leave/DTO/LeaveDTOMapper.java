package com.github.konradcz2001.medicalappointments.leave.leave.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTOMapper;
import com.github.konradcz2001.medicalappointments.leave.leave.Leave;
import org.springframework.stereotype.Service;

@Service
public class LeaveDTOMapper implements DTOMapper<LeaveDTO, Leave> {

    private final DoctorDTOMapper doctorDTOMapper;

    public LeaveDTOMapper(DoctorDTOMapper doctorDTOMapper) {
        this.doctorDTOMapper = doctorDTOMapper;
    }

    @Override
    public LeaveDTO mapToDTO(Leave source) {
        return new LeaveDTO(
                source.getId(),
                source.getStartDate(),
                source.getEndDate(),
                doctorDTOMapper.mapToDTO(source.getDoctor())
        );
    }

    @Override
    public Leave mapFromDTO(LeaveDTO sourceDTO, Leave target) {
        return target;
    }

}
