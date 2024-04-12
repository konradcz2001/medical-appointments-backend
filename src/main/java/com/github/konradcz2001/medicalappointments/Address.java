package com.github.konradcz2001.medicalappointments;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String houseNumber;  // apartment number
    String zipCode;
}
