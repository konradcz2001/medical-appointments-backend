package com.github.konradcz2001.medicalappointments.doctor.leave;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "leaves")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    LocalDateTime sinceWhen;
    LocalDateTime tillWhen;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Leave leave = (Leave) o;
        return Objects.equals(sinceWhen, leave.sinceWhen) && Objects.equals(tillWhen, leave.tillWhen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sinceWhen, tillWhen);
    }
}
