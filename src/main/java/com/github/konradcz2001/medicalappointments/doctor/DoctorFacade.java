package com.github.konradcz2001.medicalappointments.doctor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DoctorFacade {
    private final DoctorRepository repository;

    public DoctorFacade(DoctorRepository repository) {
        this.repository = repository;
    }

    public Optional<Doctor> findById(final Long id) {
        return repository.findById(id);
    }
}
