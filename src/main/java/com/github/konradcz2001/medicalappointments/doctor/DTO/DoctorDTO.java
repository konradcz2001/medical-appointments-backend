package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
 * The address field is an instance of the Address class, representing the doctor's address.
 * The class is defined as a record, which automatically generates getters, equals, hashCode, and toString methods.
 */
public record DoctorDTO(Long id,
                        @NotBlank String firstName,
                        @NotBlank String lastName,
                        String email,
                        Role role,
                        boolean isVerified,
                        byte[] avatar,
                        String profileDescription,
                        Address address) {
}
