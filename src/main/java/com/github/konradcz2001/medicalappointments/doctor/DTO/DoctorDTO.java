package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public record DoctorDTO(Long id, @NotNull String firstName, @NotNull String lastName, @NotNull @Email String email,
                        @NotNull Role role, @NotNull boolean isVerified, byte[] avatar, String profileDescription,
                        Address address) {
}
