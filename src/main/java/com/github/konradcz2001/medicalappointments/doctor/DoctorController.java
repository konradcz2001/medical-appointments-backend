package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
class DoctorController {
    private final DoctorService service;

    DoctorController(final DoctorService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<Doctor>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<Page<Doctor>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<Page<Doctor>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Page<Doctor>> readAllBySpecialization(@RequestParam String specialization, Pageable pageable){
        return service.readAllBySpecialization(specialization, pageable);
    }

    @GetMapping(path = "/available", params = "date")
    ResponseEntity<Page<Doctor>> readAllAvailableByDate(@RequestParam LocalDateTime date, Pageable pageable){
        return service.readAllAvailableByDate(date, pageable);
    }

    @PostMapping
    ResponseEntity<?> createDoctor(@RequestBody Doctor doctor){
        return service.createDoctor(doctor);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor toUpdate){
        return service.updateDoctor(id, toUpdate);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable Long id){
        return service.deleteDoctor(id);
    }


    @PatchMapping("/{id}/add-leave")
    ResponseEntity<?> addLeave(@PathVariable Long id, @RequestBody Leave leave){
        return service.addLeave(id, leave);
    }


    @PatchMapping(value = "/{doctorId}/remove-leave", params = "id")
    ResponseEntity<?> removeLeave(@PathVariable Long doctorId, @RequestParam Long id){
        return service.removeLeave(doctorId, id);
    }


    @GetMapping("/{id}/leaves")
    ResponseEntity<Page<Leave>> readAllLeaves(@PathVariable Long id, Pageable pageable){
        return service.readAllLeaves(id, pageable);
    }

    @PatchMapping("/{id}/add-specializations")
    ResponseEntity<?> addSpecializations(@PathVariable Long id, @RequestBody Set<Integer> specializationIds){
        return service.addSpecializations(id, specializationIds);
    }

    @PatchMapping(value = "/{doctorId}/remove-specialization", params = "id")
    ResponseEntity<?> removeSpecialization(@PathVariable Long doctorId, @RequestParam Integer id){
        return service.removeSpecialization(doctorId, id);
    }

    @GetMapping("/{id}/specializations")
    ResponseEntity<Set<Specialization>> readAllSpecializations(@PathVariable Long id){
        return service.readAllSpecializations(id);
    }


    @GetMapping("/{id}/reviews")
    ResponseEntity<Page<Review>> readAllReviews(@PathVariable Long id, Pageable pageable){
        return service.readAllReviews(id, pageable);
    }

}
