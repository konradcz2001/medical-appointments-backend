package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.security.Role;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * This code snippet represents a Java record class called DoctorDTO.
 * It is used to represent a doctor's data transfer object (DTO).
 * The class has several fields including id, firstName, lastName, email, role, isVerified, avatar, profileDescription, and address.
 * The firstName and lastName fields are annotated with @NotBlank to indicate that they cannot be blank.
 * The email field is not annotated, indicating that it is optional.
 * The role field is of type Role, which is an enum representing different roles.
 * The isVerified field is a boolean indicating whether the doctor is verified.
 * The avatar field is an array of bytes representing the doctor's avatar.
 * The profileDescription field is a string representing the doctor's profile description.
 * The specializations field is a set of the doctor's specializations.
 * The address field is an instance of the Address class, representing the doctor's address.
 * The class is defined as a record, which automatically generates getters, equals, hashCode, and toString methods.
 */
public record DoctorDTO(Long id,
                        @NotBlank(message = "First name must not be empty")
                        @Size(max = 255, message = "Maximum length is 255")
                        String firstName,
                        @NotBlank(message = "Last name must not be empty")
                        @Size(max = 255, message = "Maximum length is 255")
                        String lastName,
                        @NotBlank(message = "Email must not be empty")
                        @Size(max = 255, message = "Maximum length is 255")
                        @Email
                        String email,
                        @NotNull(message = "Role must not be empty")
                        Role role,
                        @NotNull(message = "Verification must not be empty")
                        boolean isVerified,
                        byte[] avatar,
                        @Size(max = 10000, message = "Maximum length is 10000 characters")
                        String profileDescription,
                        Set<SpecializationDTO> specializations,
                        Address address) {
}
