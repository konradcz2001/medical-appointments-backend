package com.github.konradcz2001.medicalappointments.contact;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@CrossOrigin
@RequiredArgsConstructor
public class EmailSenderController {
    private final EmailSenderService service;

    /**
     * Endpoint for sending an email using the provided EmailStructure.
     * Validates the EmailStructure using the @Valid annotation.
     *
     * @param emailStructure The EmailStructure object containing subject, user email, and body of the email
     * @return ResponseEntity indicating the status of the email sending process
     */
    @PostMapping
    ResponseEntity<?> sendEmail(@Valid @RequestBody EmailStructure emailStructure) {
        return service.sendEmail(emailStructure);
    }
}
