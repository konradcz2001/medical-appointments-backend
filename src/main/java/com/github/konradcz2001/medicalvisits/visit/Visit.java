package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.Client;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter(AccessLevel.PACKAGE)
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateOfVisit;
    private String type;
    private String notes;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Client client;

}
