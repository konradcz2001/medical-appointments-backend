package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.DTO.*;
import com.github.konradcz2001.medicalappointments.doctor.schedule.Schedule;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * This is a Java class representing a REST controller for managing doctors.
 * It provides various endpoints for CRUD operations on doctors, as well as operations related to leaves, specializations, and reviews.
 * The class uses Spring annotations such as @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, and @PatchMapping to define the API endpoints.
 * The class depends on the DoctorService class for handling the business logic and data access operations related to doctors.
 */
@RestController
@RequestMapping("/doctors")
@CrossOrigin
class DoctorController {
    private final DoctorService service;

    DoctorController(final DoctorService service) {
        this.service = service;
    }

    /**
     * Retrieves all doctors with pagination.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with the page of DoctorDTOs
     */
    @GetMapping
    @PermitAll
    ResponseEntity<Page<DoctorDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * Retrieves a specific doctor by their ID.
     *
     * @param id the ID of the doctor to retrieve
     * @return a ResponseEntity containing the DoctorDTO of the retrieved doctor, if found
     */
    @GetMapping("/{id}")
    @PermitAll
    ResponseEntity<DoctorDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    /**
     * Retrieves all doctors with the given first name.
     *
     * @param firstName the first name of the doctors to retrieve
     * @param pageable  the pagination information
     * @return a ResponseEntity containing a Page of DoctorDTO objects
     */
    @GetMapping(params = "firstName")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<DoctorDTO>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    /**
     * Retrieves a page of doctors with the given last name.
     *
     * @param lastName the last name of the doctors to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a page of DoctorDTO objects
     */
    @GetMapping(params = "lastName")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<DoctorDTO>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    /**
     * Retrieves a page of doctors based on the specified specialization.
     *
     * @param specialization the specialization to filter the doctors by
     * @param pageable       the pagination information
     * @return a ResponseEntity containing a page of DoctorDTO objects
     */
    @GetMapping(params = "specialization")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<DoctorDTO>> readAllBySpecialization(@RequestParam String specialization, Pageable pageable){
        return service.readAllBySpecialization(specialization, pageable);
    }

    /**
     * Retrieves all available doctors on a specific date.
     *
     * @param date     The date for which to retrieve available doctors.
     * @param pageable The pagination information.
     * @return A ResponseEntity containing a Page of DoctorDTO objects representing the available doctors.
     */
    @GetMapping(path = "/available", params = "date")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<DoctorDTO>> readAllAvailableByDate(@RequestParam LocalDateTime date, Pageable pageable){
        return service.readAllAvailableByDate(date, pageable);
    }

    /**
     * Creates a new doctor.
     *
     * @param doctor the doctor object to be created
     * @return ResponseEntity containing the created doctor DTO
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody Doctor doctor){
        return service.createDoctor(doctor);
    }

    /**
     * Updates a doctor with the specified ID.
     *
     * @param id       The ID of the doctor to update.
     * @param toUpdate The updated information of the doctor.
     * @return A ResponseEntity representing the result of the update operation.
     *         If the doctor is successfully updated, returns a ResponseEntity with no content (HTTP status code 204).
     *         If the doctor with the specified ID is not found, throws a ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO toUpdate){
        System.out.println(toUpdate.toString());
        return service.updateDoctor(id, toUpdate);
    }

    /**
     * Deletes a doctor with the specified ID.
     *
     * @param id the ID of the doctor to be deleted
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteDoctor(@PathVariable Long id){
        return service.deleteDoctor(id);
    }

    /**
     * Adds a leave for the doctor with the specified ID.
     *
     * @param id    the ID of the doctor
     * @param leave the leave to be added
     * @return a ResponseEntity representing the result of the operation
     */
    @PatchMapping("/{id}/add-leave")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> addLeave(@PathVariable Long id, @Valid @RequestBody DoctorLeaveDTO leave){
        return service.addLeave(id, leave);
    }

    /**
     * Removes a leave from a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @param id       the ID of the leave to be removed
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    @PatchMapping(value = "/{doctorId}/remove-leave", params = "id")
    ResponseEntity<?> removeLeave(@PathVariable Long doctorId, @RequestParam Long id){
        return service.removeLeave(doctorId, id);
    }


    /**
     * Retrieves all the leaves for a specific doctor.
     *
     * @param id       the ID of the doctor
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of DoctorLeaveDTO objects
     */
    @GetMapping("/{id}/leaves")
    @PermitAll
    ResponseEntity<Page<DoctorLeaveDTO>> readAllLeaves(@PathVariable Long id, Pageable pageable){
        return service.readAllLeaves(id, pageable);
    }

    /**
     * Adds specializations to a specific doctor.
     *
     * @param id                the ID of the doctor
     * @param specializationIds a Set of specialization IDs to be added
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @PatchMapping("/{id}/add-specializations")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> addSpecializations(@PathVariable Long id, @RequestBody Set<Integer> specializationIds){
        return service.addSpecializations(id, specializationIds);
    }

    /**
     * Removes a specialization from a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @param id       the ID of the specialization to be removed
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @PatchMapping(value = "/{doctorId}/remove-specialization", params = "id")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> removeSpecialization(@PathVariable Long doctorId, @RequestParam Integer id){
        return service.removeSpecialization(doctorId, id);
    }

    /**
     * Retrieves all the specializations for a specific doctor.
     *
     * @param id the ID of the doctor
     * @return a ResponseEntity containing a Set of DoctorSpecializationDTO objects
     */
    @GetMapping("/{id}/specializations")
    @PermitAll
    ResponseEntity<Set<DoctorSpecializationDTO>> readAllSpecializations(@PathVariable Long id){
        return service.readAllSpecializations(id);
    }

    /**
     * Retrieves all the reviews for a specific doctor.
     *
     * @param id       the ID of the doctor
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of DoctorReviewDTO objects
     */
    @GetMapping("/{id}/reviews")
    @PermitAll
    ResponseEntity<Page<DoctorReviewDTO>> readAllReviews(@PathVariable Long id, Pageable pageable){
        return service.readAllReviews(id, pageable);
    }

    /**
     * Search for doctors based on a given keyword and optional specialization.
     *
     * @param word The keyword to search for in doctor's information.
     * @param specialization The optional specialization to filter the search results.
     * @param pageable The pagination information for the search results.
     * @return A ResponseEntity containing a Page of DoctorDTO objects matching the search criteria.
     */
    @GetMapping(path = "/search", params = "word")
    @PermitAll
    ResponseEntity<Page<DoctorDTO>> searchDoctors(@RequestParam String word, @RequestParam(required = false) String specialization, Pageable pageable){
        return service.searchDoctors(word, specialization, pageable);
    }


    /**
     * Adds a type of visit to a specific doctor.
     *
     * @param id The ID of the doctor to add the type of visit to.
     * @param typeOfVisit The DoctorTypeOfVisitDTO object containing the information of the type of visit to add.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @PatchMapping("/{id}/add-type-of-visit")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> addTypeOfVisit(@PathVariable Long id, @Valid @RequestBody DoctorTypeOfVisitDTO typeOfVisit){
        return service.addTypeOfVisit(id, typeOfVisit);
    }


    /**
     * Removes a type of visit from a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @param id the ID of the type of visit to be removed
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @PatchMapping(value = "/{doctorId}/remove-type-of-visit", params = "id")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> removeTypeOfVisit(@PathVariable Long doctorId, @RequestParam Long id){
        return service.removeTypeOfVisit(doctorId, id);
    }


    /**
     * Retrieves all types of visits for a specific doctor.
     *
     * @param id the ID of the doctor
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of DoctorTypeOfVisitDTO objects representing the types of visits
     */
    @GetMapping("/{id}/types-of-visits")
    @PermitAll
    ResponseEntity<Page<DoctorTypeOfVisitDTO>> readAllTypesOfVisits(@PathVariable Long id, Pageable pageable){
        return service.readAllTypesOfVisits(id, pageable);
    }

    /**
     * Updates the schedule for a specific doctor.
     *
     * @param id The ID of the doctor whose schedule is being updated.
     * @param schedule The new schedule information to be set for the doctor.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @PatchMapping("/{id}/schedule")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule){
        return service.updateSchedule(id, schedule);
    }

}
