package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a data transfer object for a client.
 *
 * @param id The ID of the client.
 * @param firstName The first name of the client. Cannot be blank.
 * @param lastName The last name of the client. Cannot be blank.
 * @param email The email of the client.
 * @param role The role of the client.
 */
public record ClientDTO(Long id,
                        @NotBlank String firstName,
                        @NotBlank String lastName,
                        String email,
                        Role role) {
}
