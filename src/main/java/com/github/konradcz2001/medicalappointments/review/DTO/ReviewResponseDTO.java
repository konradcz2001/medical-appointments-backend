package com.github.konradcz2001.medicalappointments.review.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;

import java.time.LocalDateTime;

public record ReviewResponseDTO(Long id,
                                LocalDateTime date,
                                Rating rating,
                                String description,
                                Long doctorId,
                                Long clientId
                                ) {
}
