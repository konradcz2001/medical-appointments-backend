package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
class ReviewController {
    ReviewService service;

    ReviewController(final ReviewService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<ReviewDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<ReviewDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }


    @GetMapping("/between")
    ResponseEntity<Page<ReviewDTO>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

    @GetMapping("/after")
    ResponseEntity<Page<ReviewDTO>> readAllAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllAfter(after, pageable);
    }

    @GetMapping("/before")
    ResponseEntity<Page<ReviewDTO>> readAllBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBefore(before, pageable);
    }

    @GetMapping("/rating")
    ResponseEntity<Page<ReviewDTO>> readAllByRating(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRating(rating, pageable);
    }

    @GetMapping("/rating-less-than")
    ResponseEntity<Page<ReviewDTO>> readAllByRatingLessThan(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRatingLessThan(rating, pageable);
    }

    @GetMapping("/rating-greater-than")
    ResponseEntity<Page<ReviewDTO>> readAllByRatingGreaterThan(@RequestParam Rating rating, Pageable pageable){
        return service.readAllByRatingGreaterThan(rating, pageable);
    }


}
