package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctors/specializations")
@CrossOrigin
class SpecializationController {
    private final SpecializationService service;

    SpecializationController(final SpecializationService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<Specialization>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Specialization> readById(@PathVariable Integer id){
        return service.readById(id);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Specialization> readBySpecialization(@RequestParam String specialization){
        return service.readBySpecialization(specialization);
    }

    @PostMapping
    ResponseEntity<?> createSpecialization(@RequestBody Specialization specialization){
        return service.createSpecialization(specialization);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateSpecialization(@PathVariable Integer id, @RequestBody Specialization specialization){
        return service.updateSpecialization(id, specialization);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSpecialization(@PathVariable Integer id){
        return service.deleteSpecialization(id);
    }
}
