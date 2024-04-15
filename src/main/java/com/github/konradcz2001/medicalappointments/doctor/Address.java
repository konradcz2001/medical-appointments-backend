package com.github.konradcz2001.medicalappointments.doctor;

import jakarta.persistence.Column;
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
    @Column(name = "country")
    String country;
    @Column(name = "state")
    String state;   // voivodeship, ...
    @Column(name = "city")
    String city;
    @Column(name = "street")
    String street;
    @Column(name = "house_number")
    String houseNumber;  // apartment number
    @Column(name = "zip_code")
    String zipCode;
}
