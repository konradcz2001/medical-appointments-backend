package com.github.konradcz2001.medicalappointments.doctor.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.leave.leave.Leave;
import com.github.konradcz2001.medicalappointments.specialization.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

@Service
public class DoctorDTOMapper implements DTOMapper<DoctorResponseDTO, Doctor> {

    @Override
    public DoctorResponseDTO apply(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                doctor.getRole(),
                doctor.isVerified(),
                doctor.getAvatar(),
                doctor.getProfileDescription(),
                doctor.getAddress()
        );
    }

    public DoctorLeaveResponseDTO applyForLeave(Leave leave) {
        return new DoctorLeaveResponseDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate()
        );
    }


    public DoctorSpecializationResponseDTO applyForSpecialization(Specialization specialization) {
        return new DoctorSpecializationResponseDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

    public DoctorReviewResponseDTO applyForReview(Review review) {
        return new DoctorReviewResponseDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getClient().getId()
                );
    }
}
