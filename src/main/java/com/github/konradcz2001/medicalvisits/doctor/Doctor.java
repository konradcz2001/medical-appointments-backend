package com.github.konradcz2001.medicalvisits.doctor;

import com.github.konradcz2001.medicalvisits.visit.Visit;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String specialization;
    @OneToMany
    private Set<Visit> visits;

    public long getId() {
        return id;
    }

    void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }
}
