package com.github.konradcz2001.medicalappointments.review;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Page<Review> findAllByDoctorId(Long id, Pageable pageable);
    public Page<Review> findAllByClientId(Long id, Pageable pageable);
    Page<Review> findAllByDateAfterAndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Review> findAllByDateAfter(LocalDateTime after, Pageable pageable);
    Page<Review> findAllByDateBefore(LocalDateTime before, Pageable pageable);
    Page<Review> findAllByRatingLessThan(Rating rating, Pageable pageable);
    Page<Review> findAllByRatingGreaterThan(Rating rating, Pageable pageable);
    Page<Review> findAllByRating(Rating rating, Pageable pageable);

}
