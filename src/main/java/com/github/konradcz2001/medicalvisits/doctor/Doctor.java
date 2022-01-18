package com.github.konradcz2001.medicalvisits.doctor;

import com.github.konradcz2001.medicalvisits.doctor.leave.Leave;
import com.github.konradcz2001.medicalvisits.doctor.specialization.Specialization;
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

    @ManyToOne
    Specialization specialization;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Leave> leaves;

    void addLeave(Leave leave){
        leaves.add(leave);
    }

    void removeLeave(Leave leave){
        leaves.remove(leave);
    }

}
