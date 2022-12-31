package com.github.konradcz2001.medicalappointments.doctor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class DoctorDTO {
    String name;
    String surname;
    String specialization;

}
