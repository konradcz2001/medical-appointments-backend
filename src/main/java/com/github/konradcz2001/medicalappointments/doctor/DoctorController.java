package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorLeaveDTO;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTO;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorReviewDTO;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorSpecializationDTO;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    ResponseEntity<Page<DoctorDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<DoctorDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<Page<DoctorDTO>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<Page<DoctorDTO>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Page<DoctorDTO>> readAllBySpecialization(@RequestParam String specialization, Pageable pageable){
        return service.readAllBySpecialization(specialization, pageable);
    }

    @GetMapping(path = "/available", params = "date")
    ResponseEntity<Page<DoctorDTO>> readAllAvailableByDate(@RequestParam LocalDateTime date, Pageable pageable){
        return service.readAllAvailableByDate(date, pageable);
    }

    @PostMapping
    ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody Doctor doctor){
        return service.createDoctor(doctor);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO toUpdate){
        return service.updateDoctor(id, toUpdate);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable Long id){
        return service.deleteDoctor(id);
    }


    @PatchMapping("/{id}/add-leave")
    ResponseEntity<?> addLeave(@PathVariable Long id, @Valid @RequestBody Leave leave){
        return service.addLeave(id, leave);
    }


    @PatchMapping(value = "/{doctorId}/remove-leave", params = "id")
    ResponseEntity<?> removeLeave(@PathVariable Long doctorId, @RequestParam Long id){
        return service.removeLeave(doctorId, id);
    }


    @GetMapping("/{id}/leaves")
    ResponseEntity<Page<DoctorLeaveDTO>> readAllLeaves(@PathVariable Long id, Pageable pageable){
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
    ResponseEntity<Set<DoctorSpecializationDTO>> readAllSpecializations(@PathVariable Long id){
        return service.readAllSpecializations(id);
    }


    @GetMapping("/{id}/reviews")
    ResponseEntity<Page<DoctorReviewDTO>> readAllReviews(@PathVariable Long id, Pageable pageable){
        return service.readAllReviews(id, pageable);
    }

}
