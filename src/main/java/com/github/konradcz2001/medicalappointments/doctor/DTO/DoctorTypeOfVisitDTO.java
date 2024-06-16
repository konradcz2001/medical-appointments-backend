package com.github.konradcz2001.medicalappointments.doctor.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Represents a data transfer object for a doctor's type of visit.
 * <p>
 *  id: the unique identifier of the type of visit,
 *  type: the name of the visit type
 *  price: the amount of money
 *  currency: the short of currency name
 */
public record DoctorTypeOfVisitDTO(Long id,
                                   @NotBlank(message = "Type must not be empty")
                                   @Size(max = 100, message = "Maximum length is 100 characters")
                                   String type,
                                   @NotNull(message = "Price must not be empty")
                                   @DecimalMin(value = "0.0", message = "Minimum price is zero")
                                   @DecimalMax(value = "1000000000.0", message = "Maximum price is 1000000000")
                                   BigDecimal price,
                                   @NotBlank(message = "Currency must not be empty")
                                   @Size(max = 5, message = "Maximum length is 5 characters")
                                   String currency){
}

