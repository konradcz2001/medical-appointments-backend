package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.exception.MessageType;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTOMapper;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;

/**
 * This code snippet represents a class named ReviewService.
 * It is a service class that provides methods for retrieving review data.
 * The class has a constructor that takes a ReviewRepository and a ReviewDTOMapper as parameters.
 * The class includes several methods for retrieving reviews based on different criteria such as ID, date range, and rating.
 * Each method returns a ResponseEntity object containing a Page of ReviewDTO objects representing the retrieved reviews.
 * The class is annotated with the @Service annotation to indicate that it is a Spring service component.
 */
@Service
class ReviewService {
    private final ReviewRepository repository;
    private final ReviewDTOMapper dtoMapper;

    ReviewService(final ReviewRepository repository, ReviewDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Retrieves all the reviews.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing all the reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAll(Pageable pageable) {
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the ID of the review to retrieve
     * @return a ResponseEntity containing the ReviewDTO object representing the retrieved review
     * @throws ResourceNotFoundException if the review with the specified ID is not found
     */
    ResponseEntity<ReviewDTO> readById(Long id) {
        return  repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.REVIEW, id));
    }

    /**
     * Retrieves all the reviews between the specified dates.
     *
     * @param after    the minimum date to filter the reviews by
     * @param before   the maximum date to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable), dtoMapper);
    }

    /**
     * Retrieves all the reviews after the specified date.
     *
     * @param after    the minimum date to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllAfter(LocalDateTime after, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable), dtoMapper);
    }

    /**
     * Retrieves all the reviews before the specified date.
     *
     * @param before   the maximum date to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllBefore(LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable), dtoMapper);
    }

    /**
     * Retrieves all the reviews with a specific rating.
     *
     * @param rating   the rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllByRating(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRating(rating, pageable), dtoMapper);
    }

    /**
     * Retrieves all the reviews with a rating less than the specified rating.
     *
     * @param rating   the maximum rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllByRatingLessThan(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingLessThan(rating, pageable), dtoMapper);
    }


    /**
     * Retrieves all the reviews with a rating greater than the specified rating.
     *
     * @param rating   the minimum rating to filter the reviews by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ReviewDTO objects representing the filtered reviews
     */
    ResponseEntity<Page<ReviewDTO>> readAllByRatingGreaterThan(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingGreaterThan(rating , pageable), dtoMapper);
    }


}
