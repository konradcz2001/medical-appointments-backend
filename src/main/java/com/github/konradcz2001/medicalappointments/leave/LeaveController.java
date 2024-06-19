package com.github.konradcz2001.medicalappointments.leave;

import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * This is a Java class representing a REST controller for managing doctor leaves.
 * It handles various CRUD operations for leaves.
 * The class is annotated with @RestController and @RequestMapping to define the base URL for leave-related endpoints.
 * It also has @CrossOrigin annotation to allow cross-origin requests.
 */
@RestController
@RequestMapping("/doctors/leaves")
class LeaveController {
    LeaveService service;

    LeaveController(final LeaveService service) {
        this.service = service;
    }

    /**
     * Retrieves all leave records with pagination.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * Retrieves a leave record by its ID.
     *
     * @param id the ID of the leave record
     * @return a ResponseEntity containing the LeaveDTO object
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<LeaveDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    /**
     * Retrieves all leave records with a start date after the specified date and pagination.
     *
     * @param after    the start date to filter the leave records
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping("/starts-after")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAllByStartAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByStartAfter(after, pageable);
    }

    /**
     * Retrieves all leave records with an end date after the specified date and pagination.
     *
     * @param after    the end date to filter the leave records
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping("/ends-after")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAllByEndAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByEndAfter(after, pageable);
    }

    /**
     * Retrieves all leave records with a start date before the specified date and pagination.
     *
     * @param before   the start date to filter the leave records
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping("/starts-before")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAllByStartBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByStartBefore(before, pageable);
    }

    /**
     * Retrieves all leave records with an end date before the specified date and pagination.
     *
     * @param before   the end date to filter the leave records
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping("/ends-before")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAllByEndBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByEndBefore(before, pageable);
    }

    /**
     * Retrieves all leave records between the specified start and end dates with pagination.
     *
     * @param after    the start date to filter the leave records
     * @param before   the end date to filter the leave records
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of LeaveDTO objects
     */
    @GetMapping("/between")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<LeaveDTO>> readAllBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllBetween(after, before, pageable);
    }

}
