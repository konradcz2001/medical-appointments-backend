package com.github.konradcz2001.medicalappointments.review;

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
    ResponseEntity<Page<Review>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Review> readById(@PathVariable Long id){
        return service.readById(id);
    }


    @GetMapping("/between")
    ResponseEntity<Page<Review>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

    @GetMapping("/after")
    ResponseEntity<Page<Review>> readAllAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllAfter(after, pageable);
    }

    @GetMapping("/before")
    ResponseEntity<Page<Review>> readAllBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBefore(before, pageable);
    }

    @GetMapping("/rating")
    ResponseEntity<Page<Review>> readAllByRating(@RequestParam Short number, Pageable pageable){
        return service.readAllByRating(number, pageable);
    }

    @GetMapping("/rating-less-than")
    ResponseEntity<Page<Review>> readAllByRatingLessThan(@RequestParam Short number, Pageable pageable){
        return service.readAllByRatingLessThan(number, pageable);
    }

    @GetMapping("/rating-greater-than")
    ResponseEntity<Page<Review>> readAllByRatingGreaterThan(@RequestParam Short number, Pageable pageable){
        return service.readAllByRatingGreaterThan(number, pageable);
    }


}
