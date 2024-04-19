package com.github.konradcz2001.medicalappointments.doctor.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

@Service
public class DoctorDTOMapper implements DTOMapper<DoctorDTO, Doctor> {

    @Override
    public DoctorDTO mapToDTO(Doctor source) {
        return new DoctorDTO(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.getRole(),
                source.isVerified(),
                source.getAvatar(),
                source.getProfileDescription(),
                source.getAddress()
        );
    }

    @Override
    public Doctor mapFromDTO(DoctorDTO sourceDTO, Doctor target) {
        target.setFirstName(sourceDTO.firstName());
        target.setLastName(sourceDTO.lastName());
        target.setAvatar(sourceDTO.avatar());
        target.setProfileDescription(sourceDTO.profileDescription());
        target.setAddress(sourceDTO.address());

        return target;
    }

    public DoctorLeaveDTO applyForLeave(Leave leave) {
        return new DoctorLeaveDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate()
        );
    }


    public DoctorSpecializationDTO applyForSpecialization(Specialization specialization) {
        return new DoctorSpecializationDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

    public DoctorReviewDTO applyForReview(Review review) {
        return new DoctorReviewDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getClient().getId()
                );
    }
}
