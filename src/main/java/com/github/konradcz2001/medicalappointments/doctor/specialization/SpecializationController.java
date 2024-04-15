package com.github.konradcz2001.medicalappointments.doctor.specialization;

import com.github.konradcz2001.medicalappointments.doctor.specialization.DTO.SpecializationResponseDTO;
import jakarta.validation.Valid;
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
    ResponseEntity<Page<SpecializationResponseDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<SpecializationResponseDTO> readById(@PathVariable Integer id){
        return service.readById(id);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<SpecializationResponseDTO> readBySpecialization(@RequestParam String specialization){
        return service.readBySpecialization(specialization);
    }

    @PostMapping
    ResponseEntity<?> createSpecialization(@Valid @RequestBody Specialization specialization){
        return service.createSpecialization(specialization);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateSpecialization(@PathVariable Integer id, @Valid @RequestBody Specialization specialization){
        return service.updateSpecialization(id, specialization);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSpecialization(@PathVariable Integer id){
        return service.deleteSpecialization(id);
    }
}
