package com.github.konradcz2001.medicalappointments.review;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * This is a Java interface that extends the JpaRepository interface. It represents a repository for the Review entity.
 * The interface provides methods for querying the Review entities based on different criteria such as doctor ID, client ID, date range, rating, etc.
 * The methods return a Page object containing the matching Review entities, which can be used for pagination purposes.
 * The interface is annotated with the @Repository annotation to indicate that it is a Spring Data repository.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByDoctorId(Long id, Pageable pageable);
    Page<Review> findAllByClientId(Long id, Pageable pageable);
    Page<Review> findAllByDateAfterAndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Review> findAllByDateAfter(LocalDateTime after, Pageable pageable);
    Page<Review> findAllByDateBefore(LocalDateTime before, Pageable pageable);
    Page<Review> findAllByRatingLessThan(Rating rating, Pageable pageable);
    Page<Review> findAllByRatingGreaterThan(Rating rating, Pageable pageable);
    Page<Review> findAllByRating(Rating rating, Pageable pageable);

}
