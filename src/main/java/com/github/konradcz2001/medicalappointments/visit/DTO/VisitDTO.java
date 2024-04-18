package com.github.konradcz2001.medicalappointments.visit.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VisitDTO(Long id,
                       LocalDateTime date,
                       String type,
                       String notes,
                       BigDecimal price,
                       Long doctorId,
                       Long clientId
                                ) {
}
