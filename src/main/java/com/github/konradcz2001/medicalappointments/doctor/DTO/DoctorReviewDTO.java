package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * This code snippet represents a Java record called DoctorReviewDTO.
 * It is used to store information about a doctor review, including the review ID, date, rating, description, and client ID.
 * The record is used to create immutable objects that can be easily passed around and accessed.
 */
public record DoctorReviewDTO(Long id,
                              LocalDateTime date,
                              @NotNull(message = "Rating must not be empty")
                              Rating rating,
                              @Size(max = 500, message = "Maximum length is 500 characters")
                              String description,
                              @NotNull(message = "Client id must not be empty")
                              Long clientId,
                              String clientFirstName,
                              String doctorFirstName,
                              String doctorLastName
                                      ){
}

