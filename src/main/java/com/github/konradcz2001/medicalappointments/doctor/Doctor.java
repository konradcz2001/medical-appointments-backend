package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.Address;
import com.github.konradcz2001.medicalappointments.UserData;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Doctor extends UserData{
    @Embedded
    Address address;
    boolean isVerified;
    byte[] avatar;
    String profileDescription;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "specialization_id") }
    )
    Set<Specialization> specializations = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    Set<Leave> leaves = new HashSet<>();

    Doctor(final String firstName, final String lastName, final String email, final String phoneNumber, final Address address, final boolean isVerified, final byte[] avatar, final String profileDescription, final Set<Specialization> specializations, final Set<Leave> leaves) {
        super(firstName, lastName, email, phoneNumber);
        this.address = address;
        this.isVerified = isVerified;
        this.avatar = avatar;
        this.profileDescription = profileDescription;
        this.specializations = specializations;
        this.leaves = leaves;
    }

    void addSpecialization(final Specialization spec) {
        specializations.add(spec);
    }
    void removeSpecialization(Specialization spec) {
        specializations.remove(spec);
    }
    void addLeave(final Leave leave) {
        leaves.add(leave);
    }
    void removeLeave(Leave leave){
        leaves.remove(leave);
    }

}
