package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/leaves")
@CrossOrigin
class LeaveController {
    LeaveRepository repository;

    LeaveController(final LeaveRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<Page<Leave>> readAll(Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/between")
    ResponseEntity<Page<Leave>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return ResponseEntity.ok(repository.findAllBySinceWhenAfterAndTillWhenBefore(after, before, pageable));
    }
}
