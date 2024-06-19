package com.github.konradcz2001.medicalappointments.visit.type;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


/**
 * Represents a type of visit in the medical appointments system.
 * This class defines the attributes of a type of visit, including:
 * - id: The unique identifier of the type of visit.
 * - type: The type of visit.
 * - price: The price of the visit.
 * - currency: The currency in which the price is specified.
 * - duration: The duration of the visit in minutes.
 * - isActive: Is TypeOfVisit active (client has an opportunity to select it).
 * - doctor: The doctor associated with this type of visit.
 * <p>
 * The class provides getters and setters for all attributes, as well as constructors for creating instances of the class.
 * <p>
 * Note: This class uses Lombok annotations for generating getters, setters, and constructors.
 * It also uses JPA annotations for mapping the class to the database table.
 */
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

    @Column(name = "duration", nullable = false)
    @NotNull(message = "Duration must not be empty")
    @Min(value = 0, message = "Minimum duration is zero")
    Integer duration;   //Duration in minutes

    @Column(name = "is_active", nullable = false)
    boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor must not be empty")
    Doctor doctor;
}
