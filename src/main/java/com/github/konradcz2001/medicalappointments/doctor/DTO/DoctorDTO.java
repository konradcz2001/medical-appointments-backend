package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


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
