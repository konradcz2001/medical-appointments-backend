package com.github.konradcz2001.medicalappointments.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A record representing the structure of an email, including subject, user email, and body.
 * Each field is annotated with validation constraints such as @NotBlank, @Size, and @Email.
 * The subject must not be empty and have a maximum length of 100 characters.
 * The user email must not be empty, be in email format, and have a maximum length of 100 characters.
 * The body must not be empty and have a maximum length of 1000 characters.
 */
public record EmailStructure (
        @NotBlank(message = "Subject must not be empty")
        @Size(max = 100, message = "Maximum length is 100 characters")
        String subject,
        @NotBlank(message = "Email must not be empty")
        @Size(max = 100, message = "Maximum length is 100 characters")
        @Email(message = "The email must be in email format")
        String userEmail,
        @NotBlank(message = "Message must not be empty")
        @Size(max = 1000, message = "Maximum length is 1000 characters")
        String body
){
}
