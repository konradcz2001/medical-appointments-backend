package com.github.konradcz2001.medicalappointments.review.DTO;

import com.github.konradcz2001.medicalappointments.review.Rating;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReviewDTO(Long id,
                        LocalDateTime date,
                        @NotNull Rating rating,
                        String description,
                        Long doctorId,
                        Long clientId
                                ) {
}
