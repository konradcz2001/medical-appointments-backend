package com.github.konradcz2001.medicalappointments.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;

@Service
class ReviewService {
    ReviewRepository repository;

    ReviewService(final ReviewRepository repository) {
        this.repository = repository;
    }


    ResponseEntity<Page<Review>> readAll(Pageable pageable) {
        return returnResponse(() -> repository.findAll(pageable));
    }


    ResponseEntity<Page<Review>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable));
    }


    ResponseEntity<Page<Review>> readAllAfter(LocalDateTime after, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable));
    }


    ResponseEntity<Page<Review>> readAllBefore(LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable));
    }


    ResponseEntity<Page<Review>> readAllByRating(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRating(number, pageable));
    }


    ResponseEntity<Page<Review>> readAllByRatingLessThan(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingLessThan(number, pageable));
    }


    ResponseEntity<Page<Review>> readAllByRatingGreaterThan(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingGreaterThan(number, pageable));
    }

}
