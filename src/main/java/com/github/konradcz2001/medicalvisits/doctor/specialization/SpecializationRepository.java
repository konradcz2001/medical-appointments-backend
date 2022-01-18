package com.github.konradcz2001.medicalvisits.doctor.specialization;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    Specialization findFirstBySpecialization(String specialization);

}
