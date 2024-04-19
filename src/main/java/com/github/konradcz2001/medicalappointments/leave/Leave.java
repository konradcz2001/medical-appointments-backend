package com.github.konradcz2001.medicalappointments.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "leaves")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "start_date", nullable = false)
    @NotNull
    @FutureOrPresent
    LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    @NotNull
    @FutureOrPresent
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    Doctor doctor;


//    public void setDoctor(final Doctor doctor) {
//        doctor.addLeave(this);
//        this.doctor = doctor;
//    }
}
