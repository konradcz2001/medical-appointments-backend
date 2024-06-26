package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Retrieves all visits with pagination and parameter.",
            description = "All these parameters are not required. Swagger does not distinguish between individual endpoints. For more details, see the code."
    )
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @Operation(summary = "Retrieves a visit with the specified ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a specific type of visit.")
    @GetMapping(params = "type")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a date of visit after the specified date.")
    @GetMapping(params = "after")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a date of visit before the specified date.")
    @GetMapping(params = "before")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @Operation(summary = "Retrieves all visits with a date of visit between the specified after and before dates.")
    @GetMapping("/between")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a doctor ID matching the specified ID.")
    @GetMapping(params = "doctorId")
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a doctor ID matching the specified ID and a cancellation status matching the specified status.")
    @GetMapping(params = {"doctorId", "isCancelled"})
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits associated with the specified client ID.")
    @GetMapping(params = "clientId")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits associated with the specified client ID and cancellation status.")
    @GetMapping(params = {"clientId", "isCancelled"})
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with the specified price.")
    @GetMapping(params = "price")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a price less than the specified price.")
    @GetMapping(params = "maxPrice")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    //@Operation(summary = "Retrieves all visits with a price greater than the specified price.")
    @GetMapping(params = "minPrice")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @Operation(summary = "Creates a new visit.")
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
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
    @Operation(summary = "Updates a visit with the specified ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @Operation(summary = "Deletes a visit with the specified ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> deleteVisit(@PathVariable Long id){
        return service.deleteVisit(id);
    }


    /**
     * Cancels a visit with the specified ID by setting the 'cancelled' flag to true.
     *
     * @param id the ID of the visit to cancel
     * @return a ResponseEntity with no content if the visit is successfully canceled,
     *         or a ResponseEntity with an error status code if the visit is not found
     */
    @Operation(summary = "Cancels a visit with the specified ID by setting the 'cancelled' flag to true.")
    @PatchMapping("/{id}")
    ResponseEntity<?> cancelVisit(@PathVariable Long id){
        return service.cancelVisit(id);
    }

}
