package com.github.konradcz2001.medicalappointments.doctor.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/doctors/leaves")
@CrossOrigin
class LeaveController {
    LeaveService service;

    LeaveController(final LeaveService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<Leave>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/starts-after")
    ResponseEntity<Page<Leave>> readAllByStartAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByStartAfter(after, pageable);
    }

    @GetMapping("/ends-after")
    ResponseEntity<Page<Leave>> readAllByEndAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByEndAfter(after, pageable);
    }

    @GetMapping("/starts-before")
    ResponseEntity<Page<Leave>> readAllByStartBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByStartBefore(before, pageable);
    }

    @GetMapping("/ends-before")
    ResponseEntity<Page<Leave>> readAllByEndBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByEndBefore(before, pageable);
    }

    @GetMapping("/between")
    ResponseEntity<Page<Leave>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

}
