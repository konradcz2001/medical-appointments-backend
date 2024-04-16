package com.github.konradcz2001.medicalappointments.specialization.specialization;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specializations")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "specialization", unique = true, nullable = false)
    @NotBlank
    String specialization;

    //@JsonIgnore
    @ManyToMany(mappedBy = "specializations")
    Set<Doctor> doctors = new HashSet<>();

//    public void addDoctor(Doctor doctor){
//        doctors.add(doctor);
//    }
//    public void removeDoctor(Doctor doctor){
//        doctors.remove(doctor);
//    }


}
