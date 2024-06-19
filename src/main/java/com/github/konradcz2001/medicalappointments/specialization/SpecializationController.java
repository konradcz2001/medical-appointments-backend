package com.github.konradcz2001.medicalappointments.specialization;

import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents a controller for handling specializations in the medical appointments system.
 * It provides methods for retrieving, creating, updating, and deleting specializations.
 */
@RestController
@RequestMapping("/doctors/specializations")
class SpecializationController {
    private final SpecializationService service;

    SpecializationController(final SpecializationService service) {
        this.service = service;
    }

    /**
     * This method handles the HTTP GET request to retrieve all specializations.
     *
     * @param pageable The pageable object for pagination and sorting.
     * @return A ResponseEntity representing the result of the retrieval operation.
     *         If the specializations are found, it returns a ResponseEntity with a success status code (200 OK)
     *         and a Page object containing the retrieved specialization DTOs in the response body.
     */
    @GetMapping
    @PermitAll
    ResponseEntity<Page<SpecializationDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * This method handles the HTTP GET request to retrieve a specialization by its ID.
     *
     * @param id The ID of the specialization to be retrieved.
     * @return A ResponseEntity representing the result of the retrieval operation.
     *         If the specialization is found, it returns a ResponseEntity with a success status code (200 OK)
     *         and the retrieved specialization object in the response body.
     *         If the specialization is not found, it throws a ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SpecializationDTO> readById(@PathVariable Integer id){
        return service.readById(id);
    }

    /**
     * This method handles the HTTP GET request to retrieve a specialization by its name.
     *
     * @param specialization The name of the specialization to be retrieved.
     * @return A ResponseEntity representing the result of the retrieval operation.
     *         If the specialization is found, it returns a ResponseEntity with a success status code (200 OK)
     *         and the retrieved specialization object in the response body.
     *         If the specialization is not found, it throws a WrongSpecializationException.
     */
    @GetMapping(params = "specialization")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SpecializationDTO> readBySpecialization(@RequestParam String specialization){
        return service.readBySpecialization(specialization);
    }

    /**
     * This method handles the HTTP POST request to create a specialization.
     *
     * @param specialization The specialization object to be created.
     * @return A ResponseEntity representing the result of the create operation.
     *         If the create is successful, it returns a ResponseEntity with a success status code (201 Created)
     *         and the created specialization object in the response body.
     *         If the specialization already exists, it throws a WrongSpecializationException.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<SpecializationDTO> createSpecialization(@Valid @RequestBody Specialization specialization){
        return service.createSpecialization(specialization);
    }

    /**
     * This method handles the HTTP PUT request to update a specialization.
     *
     * @param id The ID of the specialization to be updated.
     * @param specialization The updated specialization data.
     * @return A ResponseEntity representing the result of the update operation.
     *         If the update is successful, it returns a ResponseEntity with a success status code (204 No Content).
     *         If the specialization with the given ID is not found, it throws a ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> updateSpecialization(@PathVariable Integer id, @Valid @RequestBody SpecializationDTO specialization){
        return service.updateSpecialization(id, specialization);
    }

    /**
     * This method handles the HTTP DELETE request to delete a specialization.
     *
     * @param id The ID of the specialization to be deleted.
     * @return A ResponseEntity representing the result of the delete operation.
     *         If the delete is successful, it returns a ResponseEntity with a success status code (204 No Content).
     *         If the specialization with the given ID is not found, it throws a ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> deleteSpecialization(@PathVariable Integer id){
        return service.deleteSpecialization(id);
    }
}
