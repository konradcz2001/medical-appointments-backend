package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
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
