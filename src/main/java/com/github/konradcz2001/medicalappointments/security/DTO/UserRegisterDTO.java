package com.github.konradcz2001.medicalappointments.security.DTO;

import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.validation.constraints.*;


public record UserRegisterDTO(
                @NotBlank(message = "First name must not be empty")
                @Size(max = 100, message = "Maximum length is 100 characters")
                String firstName,
                @NotBlank(message = "Last name must not be empty")
                @Size(max = 100, message = "Maximum length is 100 characters")
                String lastName,
                @NotBlank(message = "Email must not be empty")
                @Size(max = 100, message = "Maximum length is 100 characters")
                @Email
                String email,
                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,50}$", message = "Password must be at least 8 and maximum 50 characters long, contain at least one uppercase letter, one lowercase letter, and one digit")
                String password,
                @NotNull(message = "Role must not be empty")
                Role role
) {
}
