package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * This code snippet represents a Java class called Review.
 * It is used in the context of a medical appointments' application.
 * <p>
 * The Review class has the following properties:
 * - id: a unique identifier for the review
 * - date: the date and time when the review was created
 * - rating: the rating given in the review (1-5 stars)
 * - description: the description of the review
 * - doctor: the doctor associated with the review
 * - client: the client who wrote the review
 * <p>
 * The Review class provides the following methods:
 * - constructor: creates a new instance of the Review class with the given parameters
 * <p>
 * Note: The Review class is annotated with JPA annotations for persistence.
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "date", nullable = false)
    LocalDateTime date = LocalDateTime.now();
    @Column(name = "rating", nullable = false)
    @NotNull
    Rating rating; // 1-5 stars
    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @NotNull
    Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    Client client;
}
