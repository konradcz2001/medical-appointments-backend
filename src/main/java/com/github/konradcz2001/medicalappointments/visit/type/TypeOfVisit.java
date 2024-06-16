package com.github.konradcz2001.medicalappointments.visit.type;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "types_of_visits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeOfVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type", nullable = false)
    @NotBlank(message = "Type must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String type;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price must not be empty")
    @DecimalMin(value = "0.0", message = "Minimum price is zero")
    @DecimalMax(value = "1000000000.0", message = "Maximum price is 1000000000")
    BigDecimal price;

    @Column(name = "currency", nullable = false)
    @NotBlank(message = "Currency must not be empty")
    @Size(max = 5, message = "Maximum length is 5 characters")
    String currency;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor must not be empty")
    Doctor doctor;
}
