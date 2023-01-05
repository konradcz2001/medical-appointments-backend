package com.github.konradcz2001.medicalappointments;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    String country;
    String state;   // voivodeship, ...
    String city;
    String street;
    String number;  // house/apartment number
    String zipCode;
}
