package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "start_date", nullable = false)
    @NotNull
    LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    @NotNull
    LocalDateTime endDate;
    @JsonIgnore//TODO delete jsonignore, create dto
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    @NotNull
    Doctor doctor;


//    public void setDoctor(final Doctor doctor) {
//        doctor.addLeave(this);
//        this.doctor = doctor;
//    }
}
