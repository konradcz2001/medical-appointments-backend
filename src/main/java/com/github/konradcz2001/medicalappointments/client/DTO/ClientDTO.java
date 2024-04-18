package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.NotNull;

public record ClientDTO(Long id, @NotNull String firstName, @NotNull String lastName, String email, Role role) {
}
