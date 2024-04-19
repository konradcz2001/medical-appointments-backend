package com.github.konradcz2001.medicalappointments.visit.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VisitDTO(Long id,
                       @NotBlank @FutureOrPresent LocalDateTime date,
                       @NotBlank String type,
                       String notes,
                       @NotNull @Min(0) BigDecimal price,
                       Long doctorId,
                       Long clientId
                                ) {
}
