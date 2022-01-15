package com.github.konradcz2001.medicalvisits.doctor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String specialization;
    private boolean available;


}
