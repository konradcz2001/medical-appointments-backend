package com.github.konradcz2001.medicalappointments.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReviewFacade {
    private final ReviewRepository repository;


    public ReviewFacade(ReviewRepository repository) {
        this.repository = repository;
    }

    public Optional<Review> findById(Long id) {
        return repository.findById(id);
    }

    public Page<Review> findAllByDoctorId(Long id, Pageable pageable){
        return repository.findAllByDoctorId(id, pageable);
    }

    public Page<Review> findAllByClientId(Long id, Pageable pageable){
        return repository.findAllByClientId(id, pageable);
    }

    public void save(Review toUpdate) {
        repository.save(toUpdate);
    }
}
