package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.UserData;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import com.github.konradcz2001.medicalappointments.Address;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Doctor extends UserData{
    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    SpecializationRepository specializationRepo;
    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    LeaveRepository leaveRepo;
    @Embedded
    Address address;
    boolean isVerified;
    byte[] avatar;
    String profileDescription;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Specialization> specializations = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    Set<Leave> leaves = new HashSet<>();

    Doctor(final SpecializationRepository sr, final LeaveRepository lr) {
        this.specializationRepo = sr;
        this.leaveRepo = lr;
    }
    void addSpecialization(Specialization specialization) {
        if(!specializationRepo.existsBySpecialization(specialization.getSpecialization()))
            throw new IllegalArgumentException("There is no such specialization");
        specializations.add(specialization);
    }

    void removeSpecialization(Specialization specialization) {
        if(!specializationRepo.existsBySpecialization(specialization.getSpecialization()))
            throw new IllegalArgumentException("There is no such specialization");
        specializations.remove(specialization);
    }
    void addLeave(Leave leave){
        leaves.add(leave);
    }

    void removeLeave(Leave leave){
        if(!leaveRepo.existsById(leave.getId()))
            throw new IllegalArgumentException("There is no such leave");
        leaves.remove(leave);
    }

}
