package com.github.konradcz2001.medicalappointments.doctor.leave.DTO;


import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;

public class LeaveDTOMapper {

    public static LeaveResponseDTO apply(Leave leave) {
        return new LeaveResponseDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate(),
                DoctorDTOMapper.apply(leave.getDoctor())
        );
    }

}
