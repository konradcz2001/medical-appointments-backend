package com.github.konradcz2001.medicalappointments.doctor.specialization;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String specialization;

    /*@Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Specialization that = (Specialization) o;
        return specialization.equals(that.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization);
    }*/
}
