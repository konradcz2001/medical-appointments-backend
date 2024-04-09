package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaves")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    Doctor doctor;

    public Leave(final LocalDateTime start, final LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public void setDoctor(final Doctor doctor) {
        doctor.addLeave(this);
        this.doctor = doctor;
    }
}
