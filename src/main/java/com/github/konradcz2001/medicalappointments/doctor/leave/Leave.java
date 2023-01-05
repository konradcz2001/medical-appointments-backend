package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public Leave(final LocalDateTime sinceWhen, final LocalDateTime tillWhen) {
        this.sinceWhen = sinceWhen;
        this.tillWhen = tillWhen;
    }
}
