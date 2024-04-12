package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
    LocalDateTime startDate;
    LocalDateTime endDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    Doctor doctor;

    public Leave(final LocalDateTime startDate, final LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setDoctor(final Doctor doctor) {
        doctor.addLeave(this);
        this.doctor = doctor;
    }
}
