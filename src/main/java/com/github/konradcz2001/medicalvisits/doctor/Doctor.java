package com.github.konradcz2001.medicalvisits.doctor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String surname;
    String specialization;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Leave> leaves;

    void addLeave(Leave leave){
        leaves.add(leave);
    }

    void removeLeave(Leave leave){
        leaves.remove(leave);
    }

}
