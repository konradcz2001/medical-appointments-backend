package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This is a Java class representing a REST controller for managing medical visits.
 * It handles various CRUD operations for visits.
 * The class is annotated with @RestController and @RequestMapping to define the base URL for visit-related endpoints.
 * It also has @CrossOrigin annotation to allow cross-origin requests.
 */
@RestController
@RequestMapping("/visits")
@CrossOrigin
class VisitController {
    private final VisitService service;

    VisitController(final VisitService service) {
        this.service = service;
    }

    /**
     * Retrieves all visits with pagination.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * Retrieves a visit with the specified ID.
     *
     * @param id the ID of the visit
     * @return a ResponseEntity containing the VisitDTO object with a success status code if the visit is retrieved successfully,
     *         or a ResponseEntity with an error status code if the visit cannot be retrieved
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<VisitDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    /**
     * Retrieves all visits with a specific type of visit.
     *
     * @param type     the type of visit
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "type")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByTypeOfVisit(@RequestParam String type, Pageable pageable){
        return service.readAllByTypeOfVisit(type, pageable);
    }

    /**
     * Retrieves all visits with a date of visit after the specified date.
     *
     * @param after    the minimum date of visit (exclusive)
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "after")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByDateOfVisitAfter(after, pageable);
    }

    /**
     * Retrieves all visits with a date of visit before the specified date.
     *
     * @param before   the maximum date of visit (inclusive)
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "before")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBefore(before, pageable);
    }

    /**
     * Retrieves all visits with a date of visit between the specified after and before dates.
     *
     * @param after    the minimum date of visit (inclusive)
     * @param before   the maximum date of visit (inclusive)
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping("/between")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBetween(after, before, pageable);
    }

    /**
     * Retrieves all visits with a doctor ID matching the specified ID.
     *
     * @param doctorId       the ID of the doctor
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "doctorId")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByDoctorId(@RequestParam Long doctorId, Pageable pageable){
        return service.readAllByDoctorId(doctorId, pageable);
    }

    /**
     * Retrieves all visits with a doctor ID matching the specified ID and a cancellation status matching the specified status.
     *
     * @param doctorId       the ID of the doctor
     * @param isCancelled    the cancellation status to filter by
     * @param pageable       the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = {"doctorId", "isCancelled"})
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByDoctorIdAndCancellationStatus(@RequestParam Long doctorId, @RequestParam boolean isCancelled, Pageable pageable){
        return service.readAllByDoctorIdAndCancellationStatus(doctorId, isCancelled, pageable);
    }

    /**
     * Retrieves all visits associated with the specified client ID.
     *
     * @param clientId the ID of the client
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "clientId")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByClientId(@RequestParam Long clientId, Pageable pageable){
        return service.readAllByClientId(clientId, pageable);
    }

    /**
     * Retrieves all visits associated with the specified client ID and cancellation status.
     *
     * @param clientId the ID of the client
     * @param isCancelled the cancellation status to filter by
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = {"clientId", "isCancelled"})
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByClientIdAndCancellationStatus(@RequestParam Long clientId, @RequestParam boolean isCancelled, Pageable pageable){
        return service.readAllByClientIdAndCancellationStatus(clientId, isCancelled, pageable);
    }

    /**
     * Retrieves all visits with the specified price.
     *
     * @param price    the price for the visits
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "price")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByPrice(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPrice(price, pageable);
    }

    /**
     * Retrieves all visits with a price less than the specified price.
     *
     * @param price    the maximum price for the visits
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "maxPrice")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByPriceLessThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceLessThan(price, pageable);
    }

    /**
     * Retrieves all visits with a price greater than the specified price.
     *
     * @param price    the minimum price for the visits
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects with a success status code if the visits are retrieved successfully,
     *         or a ResponseEntity with an error status code if the visits cannot be retrieved
     */
    @GetMapping(params = "minPrice")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<VisitDTO>> readAllByPriceGreaterThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceGreaterThan(price, pageable);
    }


    /**
     * Creates a new visit.
     *
     * @param visit the VisitDTO object representing the visit to be created
     * @return a ResponseEntity with a success status code and the created VisitDTO object if the visit is created successfully,
     *         or a ResponseEntity with an error status code if the visit cannot be created
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    ResponseEntity<VisitDTO> createVisit(@Valid @RequestBody VisitDTO visit){
        return service.createVisit(visit);
    }

    /**
     * Updates a visit with the specified ID.
     *
     * @param id    the ID of the visit to be updated
     * @param visit the updated VisitDTO object
     * @return a ResponseEntity with a success status code if the visit is updated successfully,
     *         or a ResponseEntity with an error status code if the visit is not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateVisit(@PathVariable Long id, @Valid @RequestBody VisitDTO visit){
        return service.updateVisit(id, visit);
    }

    /**
     * Deletes a visit with the specified ID.
     *
     * @param id the ID of the visit to be deleted
     * @return a ResponseEntity with a success status code if the visit is deleted successfully,
     *         or a ResponseEntity with an error status code if the visit is not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteVisit(@PathVariable Long id){
        return service.deleteVisit(id);
    }


    @PatchMapping("/{id}")
    ResponseEntity<?> cancelVisit(@PathVariable Long id){
        return service.cancelVisit(id);
    }

}
