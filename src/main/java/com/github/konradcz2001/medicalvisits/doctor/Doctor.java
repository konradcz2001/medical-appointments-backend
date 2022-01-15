package com.github.konradcz2001.medicalvisits.doctor;

import com.github.konradcz2001.medicalvisits.visit.Visit;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String specialization;
    private boolean available;
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

    boolean isAvailable() {
        return available;
    }

    void setAvailable(final boolean available) {
        this.available = available;
    }

}
