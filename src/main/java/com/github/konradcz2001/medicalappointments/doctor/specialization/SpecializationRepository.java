package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    Optional<Specialization> findFirstBySpecialization(String specialization);
    boolean existsBySpecialization(String specialization);
}
