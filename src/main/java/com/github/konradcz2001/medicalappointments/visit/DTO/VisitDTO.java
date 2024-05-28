package com.github.konradcz2001.medicalappointments.visit.DTO;

import jakarta.validation.constraints.*;

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
                       @NotNull(message = "Date must not be empty")
                       @FutureOrPresent(message = "Date must not be in the past")
                       LocalDateTime date,
                       @NotBlank(message = "Type must not be empty")
                       @Size(max = 100, message = "Maximum length is 100 characters")
                       String type,
                       @Size(max = 500, message = "Maximum length is 500 characters")
                       String notes,
                       @NotNull(message = "Price must not be empty")
                       @DecimalMin(value = "0.0", message = "Minimum price is zero")
                       @DecimalMax(value = "1000000000.0", message = "Maximum price is 1000000000")
                       BigDecimal price,
                       @NotNull(message = "Doctor id must not be empty")
                       Long doctorId,
                       @NotNull(message = "Client must not be empty")
                       Long clientId
                                ) {
}
