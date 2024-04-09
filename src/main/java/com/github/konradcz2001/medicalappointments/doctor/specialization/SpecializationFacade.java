package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpecializationFacade {
    private final SpecializationRepository repository;


    public SpecializationFacade(SpecializationRepository repository) {
        this.repository = repository;
    }

    public Optional<Specialization> findById(Integer id) {
        return repository.findById(id);
    }
}
