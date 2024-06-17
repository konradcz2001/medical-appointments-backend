package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.doctor.schedule.Schedule;
import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

/**
 * Represents a data transfer object for a doctor, containing information such as id, first name, last name, email, role, verification status, avatar, profile description,
 * specializations, address, types of visits, and schedule.
 */
public record DoctorDTO(Long id,
                        @NotBlank(message = "First name must not be empty")
                        @Size(max = 255, message = "Maximum length is 255")
                        String firstName,
                        @NotBlank(message = "Last name must not be empty")
                        @Size(max = 255, message = "Maximum length is 255")
                        String lastName,
                        String email,
                        Role role,
                        boolean isVerified,
                        byte[] avatar,
                        @Size(max = 10000, message = "Maximum length is 10000 characters")
                        String profileDescription,
                        Set<DoctorSpecializationDTO> specializations,
                        Address address,
                        List<DoctorTypeOfVisitDTO> typesOfVisits,
                        Schedule schedule) {
}
