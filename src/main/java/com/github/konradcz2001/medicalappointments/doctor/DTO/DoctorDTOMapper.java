package com.github.konradcz2001.medicalappointments.doctor.DTO;


import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;

public class DoctorDTOMapper {

    public static DoctorResponseDTO apply(Doctor doctor) {
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

    public static DoctorLeaveResponseDTO applyForLeave(Leave leave) {
        return new DoctorLeaveResponseDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate()
        );
    }


    public static DoctorSpecializationResponseDTO applyForSpecialization(Specialization specialization) {
        return new DoctorSpecializationResponseDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

    public static DoctorReviewResponseDTO applyForReview(Review review) {
        return new DoctorReviewResponseDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getClient().getId()
                );
    }
}
