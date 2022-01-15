package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorFacade {
    private final DoctorRepository repository;

    DoctorFacade(final DoctorRepository repository) {
        this.repository = repository;
    }


    public Optional<Doctor> findById(final long id) {
        return repository.findById(id);
    }
}
