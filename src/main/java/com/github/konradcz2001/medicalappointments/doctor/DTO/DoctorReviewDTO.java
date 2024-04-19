package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;

import java.time.LocalDateTime;

/**
 * This code snippet represents a Java record called DoctorReviewDTO.
 * It is used to store information about a doctor review, including the review ID, date, rating, description, and client ID.
 * The record is used to create immutable objects that can be easily passed around and accessed.
 */
public record DoctorReviewDTO(Long id,
                              LocalDateTime date,
                              Rating rating,
                              String description,
                              Long clientId
                                      ){
}

