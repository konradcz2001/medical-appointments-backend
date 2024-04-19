package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @FutureOrPresent
    @NotBlank
    LocalDateTime date;
    @Column(name = "type", nullable = false)
    @NotBlank
    String type;
    @Column(name = "notes")
    String notes;
    @Column(name = "price", nullable = false)
    @NotNull
    @Min(0)
    BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull
    Client client;

}
