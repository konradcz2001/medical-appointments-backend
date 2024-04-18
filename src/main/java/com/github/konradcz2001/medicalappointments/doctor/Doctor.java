package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.common.User;
import com.github.konradcz2001.medicalappointments.leave.leave.Leave;
import com.github.konradcz2001.medicalappointments.specialization.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Doctor extends User {
    @Embedded
    Address address;
    @Column(name = "is_verified", nullable = false)
    @NotNull
    boolean isVerified;
    @Column(name = "avatar")
    byte[] avatar;
    @Column(name = "profile_description")
    String profileDescription;

    @OneToMany(mappedBy = "doctor")
    List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "doctor_specialization",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "specialization_id") }
    )
    Set<Specialization> specializations = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    List<Leave> leaves = new ArrayList<>();



    void addSpecialization(Specialization spec) {
        specializations.add(spec);
    }
    public void removeSpecialization(Specialization spec) {
        specializations.remove(spec);
    }
    public void addLeave(Leave leave) {
        leaves.add(leave);
    }
    void removeLeave(Leave leave){
        leaves.remove(leave);
    }


}
