package com.github.konradcz2001.medicalappointments.client.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;

import java.time.LocalDateTime;

public record ClientReviewResponseDTO(Long id,
                                      LocalDateTime date,
                                      Rating rating,
                                      String description,
                                      Long doctorId
                                      ){
}

