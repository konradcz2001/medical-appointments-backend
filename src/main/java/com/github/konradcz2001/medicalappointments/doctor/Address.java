package com.github.konradcz2001.medicalappointments.doctor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * This code snippet represents a Java class called Address.
 * It is annotated with @Embeddable, indicating that it can be embedded within other entities.
 * The class has private fields for country, state, city, street, house number, and zip code,
 * all of which are annotated with @Column to specify their corresponding column names in a database table.
 * The class also includes getters and setters for these fields,
 * as well as a no-argument constructor and an all-argument constructor.
 */
@Embeddable
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Column(name = "country")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String country;
    @Column(name = "state")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String state;   // voivodeship, ...
    @Column(name = "city")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String city;
    @Column(name = "street")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String street;
    @Column(name = "house_number")
    @Size(max = 10, message = "Maximum length is 10 characters")
    String houseNumber;  // apartment number
    @Column(name = "zip_code")
    @Size(max = 10, message = "Maximum length is 10 characters")
    String zipCode;
}
