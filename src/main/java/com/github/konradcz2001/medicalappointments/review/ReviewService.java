package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.MessageType;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTOMapper;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
class ReviewService {
    ReviewRepository repository;

    ReviewService(final ReviewRepository repository) {
        this.repository = repository;
    }

    private ResponseEntity<Page<ReviewResponseDTO>> returnResponse(Supplier<Page<Review>> suppliedReviews) {
        var reviews = suppliedReviews.get()
                .map(ReviewDTOMapper::apply);
        if(reviews.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(reviews);
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAll(Pageable pageable) {
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<ReviewResponseDTO> readById(Long id) {
        return  repository.findById(id)
                .map(ReviewDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.REVIEW, id));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllAfter(LocalDateTime after, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllBefore(LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllByRating(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRating(number, pageable));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllByRatingLessThan(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingLessThan(number, pageable));
    }


    ResponseEntity<Page<ReviewResponseDTO>> readAllByRatingGreaterThan(Short number, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingGreaterThan(number, pageable));
    }


}
