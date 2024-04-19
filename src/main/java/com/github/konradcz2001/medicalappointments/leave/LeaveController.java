package com.github.konradcz2001.medicalappointments.leave;

import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTO;
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
    ResponseEntity<Page<LeaveDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<LeaveDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping("/starts-after")
    ResponseEntity<Page<LeaveDTO>> readAllByStartAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByStartAfter(after, pageable);
    }

    @GetMapping("/ends-after")
    ResponseEntity<Page<LeaveDTO>> readAllByEndAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByEndAfter(after, pageable);
    }

    @GetMapping("/starts-before")
    ResponseEntity<Page<LeaveDTO>> readAllByStartBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByStartBefore(before, pageable);
    }

    @GetMapping("/ends-before")
    ResponseEntity<Page<LeaveDTO>> readAllByEndBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByEndBefore(before, pageable);
    }

    @GetMapping("/between")
    ResponseEntity<Page<LeaveDTO>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

}
