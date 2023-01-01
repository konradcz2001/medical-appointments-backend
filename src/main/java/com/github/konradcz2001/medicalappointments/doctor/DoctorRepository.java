package com.github.konradcz2001.medicalappointments.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByFirstName(String firstName, Pageable pageable);
    Page<Doctor> findAllByLastName(String lastName, Pageable pageable);
    Page<Doctor> findAllBySpecializations_Specialization(String specialization, Pageable pageable);
    Page<Doctor> findAllByVerifiedIsTrue(Pageable pageable);
}
