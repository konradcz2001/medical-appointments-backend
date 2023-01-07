package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "leaves")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime sinceWhen;
    LocalDateTime tillWhen;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    Doctor doctor;

    public Leave(final LocalDateTime sinceWhen, final LocalDateTime tillWhen) {
        this.sinceWhen = sinceWhen;
        this.tillWhen = tillWhen;
    }

    public void setDoctor(final Doctor doctor) {
        doctor.addLeave(this);
        this.doctor = doctor;
    }
}
