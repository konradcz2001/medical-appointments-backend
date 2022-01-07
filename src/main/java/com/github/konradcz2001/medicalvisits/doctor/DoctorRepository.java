package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByName(String name);
    List<Doctor> findAllBySurname(String surname);
    List<Doctor> findAllBySpecialization(String specialization);
}
