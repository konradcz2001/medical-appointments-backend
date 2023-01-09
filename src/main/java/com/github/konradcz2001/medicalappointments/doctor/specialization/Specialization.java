package com.github.konradcz2001.medicalappointments.doctor.specialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import lombok.*;
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
    Integer id;
    @Column(unique = true)
    String specialization;

    @JsonIgnore
    @ManyToMany(mappedBy = "specializations")
    Set<Doctor> doctors = new HashSet<>();

    public Specialization(final String specialization) {
        this.specialization = specialization;
    }

    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
    }
    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }


}
