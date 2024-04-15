package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;

import java.time.LocalDateTime;

public record DoctorReviewResponseDTO(Long id,
                                      LocalDateTime date,
                                      Rating rating,
                                      String description,
                                      Long clientId
                                      ){
}

