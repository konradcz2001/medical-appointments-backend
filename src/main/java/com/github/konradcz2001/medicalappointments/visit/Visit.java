package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
    @Column(name = "notes")
    @Size(max = 500, message = "Maximum length is 500 characters")
    String notes;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    @NotNull(message = "Type of visit must not be empty")
    TypeOfVisit typeOfVisit;
    @Column(name = "is_cancelled", nullable = false)
    boolean isCancelled;
//    @ManyToOne
//    @JoinColumn(name = "doctor_id", nullable = false)
//    @NotNull(message = "Doctor must not be empty")
//    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "Client must not be empty")
    Client client;

}
