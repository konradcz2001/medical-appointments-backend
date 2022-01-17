package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.Client;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    LocalDateTime dateOfVisit;
    String type;
    String notes;
    @ManyToOne
    Doctor doctor;
    @ManyToOne
    Client client;

}
