package com.github.konradcz2001.medicalappointments.review;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByDoctorId(Long id, Pageable pageable);
    Page<Review> findAllByClientId(Long id, Pageable pageable);
    Page<Review> findAllByDateAfterAndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Review> findAllByDateAfter(LocalDateTime after, Pageable pageable);
    Page<Review> findAllByDateBefore(LocalDateTime before, Pageable pageable);
    Page<Review> findAllByRatingLessThan(Short number, Pageable pageable);
    Page<Review> findAllByRatingGreaterThan(Short number, Pageable pageable);
    Page<Review> findAllByRating(Short number, Pageable pageable);

}
