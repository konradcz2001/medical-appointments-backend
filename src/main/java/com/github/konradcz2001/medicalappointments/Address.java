package com.github.konradcz2001.medicalappointments;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    String country;
    String state;
    String city;
    String street;
    String number;
    String zipCode;

}
