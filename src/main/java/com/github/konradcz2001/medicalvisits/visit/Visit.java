package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.customer.Customer;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Visit {
    @Id
    @GeneratedValue
    long id;
    LocalDateTime deadline;
    String type;
    String notes;
    @ManyToOne
    Doctor doctor;
    @ManyToOne
    Customer customer;

}
