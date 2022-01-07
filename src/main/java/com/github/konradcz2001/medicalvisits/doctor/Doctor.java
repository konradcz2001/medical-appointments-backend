package com.github.konradcz2001.medicalvisits.doctor;

import com.github.konradcz2001.medicalvisits.visit.Visit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    @Id
    @GeneratedValue
    long id;
    String name;
    String surname;
    String specialization;
    @OneToMany
    Set<Visit> visits;

}
