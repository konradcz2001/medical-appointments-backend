package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime dateOfVisit;
    String type;
    String notes;
    BigDecimal price;
    @ManyToOne
    Doctor doctor;
    @ManyToOne
    Client client;

}
