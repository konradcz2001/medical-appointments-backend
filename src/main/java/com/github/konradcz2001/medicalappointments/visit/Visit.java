package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This code snippet represents a Java class called Visit.
 * <p>
 * The Visit class has the following properties:
 * - id: a unique identifier for the visit
 * - date: the date of the visit
 * - type: the type of the visit
 * - notes: additional notes for the visit
 * - price: the price of the visit
 * - doctor: the doctor associated with the visit
 * - client: the client associated with the visit
 * <p>
 * Note: The Visit class is annotated with JPA annotations for persistence and validation.
 */
@Entity
@Table(name = "visits")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "date", nullable = false)
    @NotNull(message = "Date must not be empty")
    @FutureOrPresent(message = "Date must not be in the past")
    LocalDateTime date;
    @Column(name = "type", nullable = false)
    @NotBlank(message = "Type must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String type;
    @Column(name = "notes")
    @Size(max = 500, message = "Maximum length is 500 characters")
    String notes;
    @Column(name = "price", nullable = false)
    @NotNull(message = "Price must not be empty")
    @DecimalMin(value = "0.0", message = "Minimum price is zero")
    @DecimalMax(value = "1000000000.0", message = "Maximum price is 1000000000")
    BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor must not be empty")
    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "Client must not be empty")
    Client client;

}
