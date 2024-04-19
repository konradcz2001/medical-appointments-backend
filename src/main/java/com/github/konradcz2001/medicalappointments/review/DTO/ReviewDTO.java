package com.github.konradcz2001.medicalappointments.review.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


/**
 * Represents a data transfer object for Review information.
 * <p>
 * This class is used to transfer Review data between different layers of the application.
 * It contains the following properties:
 * - id: a Long representing the ID of the review
 * - date: a LocalDateTime representing the date of the review
 * - rating: a Rating representing the rating of the review (cannot be null)
 * - description: a String representing the description of the review
 * - doctorId: a Long representing the ID of the doctor associated with the review
 * - clientId: a Long representing the ID of the client associated with the review
 */
public record ReviewDTO(Long id,
                        LocalDateTime date,
                        @NotNull Rating rating,
                        String description,
                        Long doctorId,
                        Long clientId
                                ) {
}
