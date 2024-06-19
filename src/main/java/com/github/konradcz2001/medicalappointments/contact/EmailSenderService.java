package com.github.konradcz2001.medicalappointments.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String fromMail;

    @Value("${spring.mail.to.username}")
    String toMail;

    /**
     * Sends an email using the provided EmailStructure object.
     * Constructs a SimpleMailMessage with the email subject, user email, and body.
     * Sets the sender's email address, recipient's email address, subject, and message content.
     * Sends the email using the configured mail sender.
     *
     * @param emailStructure the EmailStructure object containing the email details
     * @return ResponseEntity with status 200 (OK) indicating the email was sent successfully
     */
    //TODO check why it takes so long to send email
    ResponseEntity<?> sendEmail(EmailStructure emailStructure) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toMail);
        message.setSubject(emailStructure.subject());
        message.setText("User e-mail: " + emailStructure.userEmail() + "\n\nMessage: \n" + emailStructure.body());
        mailSender.send(message);

        return ResponseEntity.ok().build();
    }
}
