package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * This is a Java class representing a REST controller for managing doctor reviews.
 * It handles various CRUD operations for reviews.
 * The class is annotated with @RestController and @RequestMapping to define the base URL for review-related endpoints.
 * It also has @CrossOrigin annotation to allow cross-origin requests.
 */
@RestController
@RequestMapping("/reviews")
class ReviewController {
    ReviewService service;

    ReviewController(final ReviewService service) {
        this.service = service;
    }

    /**
     * Retrieves all reviews.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the ID of the review to retrieve
     * @return a ResponseEntity containing the ReviewDTO object
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ReviewDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }


    /**
     * Retrieves all reviews between the specified dates and times.
     *
     * @param after    the minimum date and time to filter the reviews by
     * @param before   the maximum date and time to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/between")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

    /**
     * Retrieves all reviews after the specified date and time.
     *
     * @param after    the minimum date and time to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/after")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllAfter(after, pageable);
    }

    /**
     * Retrieves all reviews before the specified date and time.
     *
     * @param before   the maximum date and time to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/before")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBefore(before, pageable);
    }

    /**
     * Retrieves all reviews with the specified rating.
     *
     * @param rating   the rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/rating")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllByRating(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRating(rating, pageable);
    }

    /**
     * Retrieves all reviews with a rating less than the specified rating.
     *
     * @param rating   the maximum rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/rating-less-than")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllByRatingLessThan(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRatingLessThan(rating, pageable);
    }

    /**
     * Retrieves all reviews with a rating greater than the specified rating.
     *
     * @param rating   the minimum rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects
     */
    @GetMapping("/rating-greater-than")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ReviewDTO>> readAllByRatingGreaterThan(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRatingGreaterThan(rating, pageable);
    }


}
