package com.github.konradcz2001.medicalappointments.doctor.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * This code snippet represents a Java class named "DoctorDTOMapper" that implements the "DTOMapper" interface.
 * It is a service class used for mapping between Doctor and DoctorDTO objects.
 * <p>
 * The class provides methods to map a Doctor object to a DoctorDTO object and vice versa.
 * It also provides additional methods to map Leave, Specialization, TypeOfVisit and Review objects to their respective DTOs.
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
                        new DoctorSpecializationDTO(specialization.getId(), specialization.getSpecialization()))
                        .collect(Collectors.toSet()),
                source.getAddress(),
                source.getTypesOfVisits().stream().map(type ->
                                new DoctorTypeOfVisitDTO(type.getId(), type.getType(), type.getPrice(), type.getCurrency(), type.getDuration(), type.isActive()))
                        .collect(Collectors.toList()),
                source.getSchedule()
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

    public Leave mapFromDoctorLeaveDTO(DoctorLeaveDTO sourceDTO, Leave target) {
        target.setStartDate(sourceDTO.startDate());
        target.setEndDate(sourceDTO.endDate());

        return target;
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
                review.getClient().getId(),
                review.getClient().getFirstName(),
                review.getDoctor().getFirstName(),
                review.getDoctor().getLastName()
                );
    }

    public DoctorTypeOfVisitDTO mapToDoctorTypeOfVisitDTO(TypeOfVisit type) {
        return new DoctorTypeOfVisitDTO(
                type.getId(),
                type.getType(),
                type.getPrice(),
                type.getCurrency(),
                type.getDuration(),
                type.isActive()
        );
    }

    public TypeOfVisit mapFromDoctorTypeOfVisitDTO(DoctorTypeOfVisitDTO sourceDTO, TypeOfVisit target) {
        target.setType(sourceDTO.type());
        target.setPrice(sourceDTO.price());
        target.setCurrency(sourceDTO.currency());
        target.setDuration(sourceDTO.duration());
        target.setActive(sourceDTO.isActive());

        return target;
    }
}
