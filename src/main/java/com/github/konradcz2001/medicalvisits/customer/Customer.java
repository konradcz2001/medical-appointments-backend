package com.github.konradcz2001.medicalvisits.customer;

import com.github.konradcz2001.medicalvisits.visit.Visit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue
    long id;
    String name;
    String surname;
    @OneToMany
    Set<Visit> visits;

}
