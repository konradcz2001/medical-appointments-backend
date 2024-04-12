package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.Address;
import com.github.konradcz2001.medicalappointments.User;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
//TODO annotation validation
//TODO record DTO

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Doctor extends User {
    @Embedded
    Address address;
    boolean isVerified;
    byte[] avatar;
    String profileDescription;

    @OneToMany(mappedBy = "doctor")
    Set<Review> reviews = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "specialization_id") }
    )
    Set<Specialization> specializations = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    Set<Leave> leaves = new HashSet<>();



    Doctor(final String firstName, final String lastName, final String email, final String phoneNumber,
           final Address address, final boolean isVerified, final byte[] avatar, final String profileDescription) {
        super(firstName, lastName, email, phoneNumber);
        this.address = address;
        this.isVerified = isVerified;
        this.avatar = avatar;
        this.profileDescription = profileDescription;
    }

    void addSpecialization(final Specialization spec) {
        spec.addDoctor(this);
        specializations.add(spec);
    }
    public void removeSpecialization(Specialization spec) {
        spec.removeDoctor(this);
        specializations.remove(spec);
    }
    public void addLeave(final Leave leave) {
        leaves.add(leave);
    }
    void removeLeave(Leave leave){
        leaves.remove(leave);
    }


}
