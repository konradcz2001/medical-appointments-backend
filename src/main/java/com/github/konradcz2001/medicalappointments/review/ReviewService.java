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

@Service
class ReviewService {
    private final ReviewRepository repository;
    private final ReviewDTOMapper dtoMapper;

    ReviewService(final ReviewRepository repository, ReviewDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }


    ResponseEntity<Page<ReviewDTO>> readAll(Pageable pageable) {
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    ResponseEntity<ReviewDTO> readById(Long id) {
        return  repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.REVIEW, id));
    }


    ResponseEntity<Page<ReviewDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable), dtoMapper);
    }


    ResponseEntity<Page<ReviewDTO>> readAllAfter(LocalDateTime after, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable), dtoMapper);
    }


    ResponseEntity<Page<ReviewDTO>> readAllBefore(LocalDateTime before, Pageable pageable) {
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable), dtoMapper);
    }


    ResponseEntity<Page<ReviewDTO>> readAllByRating(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRating(rating, pageable), dtoMapper);
    }


    ResponseEntity<Page<ReviewDTO>> readAllByRatingLessThan(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingLessThan(rating, pageable), dtoMapper);
    }


    ResponseEntity<Page<ReviewDTO>> readAllByRatingGreaterThan(Rating rating, Pageable pageable) {
        return returnResponse(() -> repository.findAllByRatingGreaterThan(rating , pageable), dtoMapper);
    }


}
