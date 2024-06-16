package com.github.konradcz2001.medicalappointments.visit.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Represents a data transfer object for a type of visit.
 * <p>
 *  id: the unique identifier of the type of visit,
 *  type: the name of the visit type
 *  price: the amount of money
 *  currency: the short of currency name
 *  duration: duration of the visit in minutes
 *  doctorId: the id of doctor
 */
public record TypeOfVisitDTO(Long id,
                             @NotBlank(message = "Type must not be empty")
                             @Size(max = 100, message = "Maximum length is 100 characters")
                             String type,
                             @NotNull(message = "Price must not be empty")
                             @DecimalMin(value = "0.0", message = "Minimum price is zero")
                             @DecimalMax(value = "1000000000.0", message = "Maximum price is 1000000000")
                             BigDecimal price,
                             @NotBlank(message = "Currency must not be empty")
                             @Size(max = 5, message = "Maximum length is 5 characters")
                             String currency,
                             @NotNull(message = "Duration must not be empty")
                             @Min(value = 0, message = "Minimum duration is zero")
                             Integer duration,   //Duration in minutes
                             @NotNull(message = "Doctor id must not be empty")
                             Long doctorId){
}

