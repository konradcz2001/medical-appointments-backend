package com.github.konradcz2001.medicalappointments.visit.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Represents a data transfer object for a visit.
 * <p>
 *  id: the unique identifier of the visit,
 *  date: the date and time of the visit
 *  notes: additional notes for the visit
 *  typeOfVisit: the type of visit
 *  isCancelled: flag indicating if the visit is cancelled
 *  clientId: the unique identifier of the client
 */
public record VisitDTO(Long id,
                       @NotNull(message = "Date must not be empty")
                       @FutureOrPresent(message = "Date must not be in the past")
                       LocalDateTime date,
                       @Size(max = 500, message = "Maximum length is 500 characters")
                       String notes,
                       @NotNull(message = "Type of visit must not be empty")
                       TypeOfVisitDTO typeOfVisit,
                       boolean isCancelled,
                       @NotNull(message = "Client must not be empty")
                       Long clientId
                                ) {
}
