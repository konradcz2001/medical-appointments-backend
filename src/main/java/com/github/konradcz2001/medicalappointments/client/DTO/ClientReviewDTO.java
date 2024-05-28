package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Represents a data transfer object (DTO) for a client's review.
 * <p>
 * This DTO contains the following information:
 * - id: The unique identifier of the review.
 * - date: The date and time when the review was created.
 * - rating: The rating given to the doctor by the client.
 * - description: The description or comments provided by the client.
 * - doctorId: The unique identifier of the doctor being reviewed.
 */
public record ClientReviewDTO(Long id,
                              LocalDateTime date,
                              @NotNull(message = "Rating must not be empty")
                              Rating rating,
                              @Size(max = 500, message = "Maximum length is 500 characters")
                              String description,
                              @NotNull(message = "Doctor id must not be empty")
                              Long doctorId
                                      ){
}

