package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;

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
                              Rating rating,
                              String description,
                              Long doctorId
                                      ){
}

