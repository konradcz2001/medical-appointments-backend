package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.security.Role;

public record ClientResponseDTO(Long id, String firstName, String lastName, String email, Role role) {
}
