package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.common.User;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This code snippet represents a Java class named "Doctor" that extends the "User" class.
 * It is an entity class that is mapped to a database table named "doctors".
 * <p>
 * The "Doctor" class has the following attributes:
 * - address: An embedded object representing the address of the doctor.
 * - isVerified: A boolean indicating whether the doctor is verified.
 * - avatar: A byte array representing the avatar of the doctor.
 * - profileDescription: A string representing the profile description of the doctor.
 * - reviews: A list of reviews associated with the doctor.
 * - specializations: A set of specializations that the doctor has.
 * - leaves: A list of leaves taken by the doctor.
 * <p>
 * The class provides methods to add and remove specializations and leaves.
 * <p>
 * Note: The class uses Lombok annotations for generating getters, setters, and default constructor.
 * It also uses JPA annotations for mapping the class to the database table.
 */
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
    @NotNull(message = "Verification must not be empty")
    boolean isVerified;
    @Column(name = "avatar")
    byte[] avatar;
    @Column(name = "profile_description")
    @Size(max = 10000, message = "Maximum length is 10000 characters")
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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Leave> leaves = new ArrayList<>();


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TypeOfVisit> typesOfVisits = new ArrayList<>();

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
    public void addTypeOfVisit(TypeOfVisit type) {
        typesOfVisits.add(type);
    }
    void removeTypeOfVisit(TypeOfVisit type){
        typesOfVisits.remove(type);
    }


}
