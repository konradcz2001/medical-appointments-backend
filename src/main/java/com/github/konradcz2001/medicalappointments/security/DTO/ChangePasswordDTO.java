package com.github.konradcz2001.medicalappointments.security.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        @NotBlank(message = "Current password must not be empty")
        String currentPassword,

        @NotBlank(message = "New password must not be empty")
        @Size(min = 8, message = "New password must be at least 8 characters long")
        String newPassword
) {}