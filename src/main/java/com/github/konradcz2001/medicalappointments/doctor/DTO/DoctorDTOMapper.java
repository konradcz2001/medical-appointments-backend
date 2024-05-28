package com.github.konradcz2001.medicalappointments.doctor.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * This code snippet represents a Java class named "DoctorDTOMapper" that implements the "DTOMapper" interface.
 * It is a service class used for mapping between Doctor and DoctorDTO objects.
 * <p>
 * The class provides methods to map a Doctor object to a DoctorDTO object and vice versa.
 * It also provides additional methods to map Leave, Specialization, and Review objects to their respective DTOs.
 * <p>
 * Note: The class is annotated with @Service to indicate that it is a Spring service component.
 */
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
                source.getSpecializations().stream().map(specialization ->
                        new SpecializationDTO(specialization.getId(), specialization.getSpecialization()))
                        .collect(Collectors.toSet()),
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

    public DoctorLeaveDTO mapToDoctorLeaveDTO(Leave leave) {
        return new DoctorLeaveDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate()
        );
    }


    public DoctorSpecializationDTO mapToDoctorSpecializationDTO(Specialization specialization) {
        return new DoctorSpecializationDTO(
                specialization.getId(),
                specialization.getSpecialization()
        );
    }

    public DoctorReviewDTO mapToDoctorReviewDTO(Review review) {
        return new DoctorReviewDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getClient().getId()
                );
    }
}
