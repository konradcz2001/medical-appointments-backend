package com.github.konradcz2001.medicalappointments.visit.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a data transfer object for a visit.
 * <p>
 * This class is a record that encapsulates the information related to a visit, including its ID, date, type, notes, price, doctor ID, and client ID.
 * The visit date must be a future or present date and cannot be blank.
 * The visit type cannot be blank.
 * The visit price must be a non-null value and greater than or equal to 0.
 * The doctor ID and client ID are optional.
 */
public record VisitDTO(Long id,
                       @NotBlank @FutureOrPresent LocalDateTime date,
                       @NotBlank String type,
                       String notes,
                       @NotNull @Min(0) BigDecimal price,
                       Long doctorId,
                       Long clientId
                                ) {
}
