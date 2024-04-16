package com.github.konradcz2001.medicalappointments.leave.leave.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTOMapper;
import com.github.konradcz2001.medicalappointments.leave.leave.Leave;
import org.springframework.stereotype.Service;

@Service
public class LeaveDTOMapper implements DTOMapper<LeaveResponseDTO, Leave> {

    private final DoctorDTOMapper doctorDTOMapper;

    public LeaveDTOMapper(DoctorDTOMapper doctorDTOMapper) {
        this.doctorDTOMapper = doctorDTOMapper;
    }

    @Override
    public LeaveResponseDTO apply(Leave leave) {
        return new LeaveResponseDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate(),
                doctorDTOMapper.apply(leave.getDoctor())
        );
    }

}
