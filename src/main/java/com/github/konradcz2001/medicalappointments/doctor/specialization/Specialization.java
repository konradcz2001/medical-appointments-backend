package com.github.konradcz2001.medicalappointments.doctor.specialization;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String specialization;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "specialization_doctor",
            joinColumns = { @JoinColumn(name = "specialization_id") },
            inverseJoinColumns = { @JoinColumn(name = "doctor_id") }
    )
    Set<Doctor> doctors = new HashSet<>();

    Specialization(final String specialization) {
        this.specialization = specialization;
    }
}
