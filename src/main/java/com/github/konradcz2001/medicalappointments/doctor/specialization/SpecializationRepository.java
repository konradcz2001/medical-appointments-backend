package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    Specialization findFirstBySpecialization(String specialization);
    boolean existsBySpecialization(String specialization);
}
