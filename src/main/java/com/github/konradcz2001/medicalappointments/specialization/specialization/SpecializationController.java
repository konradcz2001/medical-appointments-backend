package com.github.konradcz2001.medicalappointments.specialization.specialization;

import com.github.konradcz2001.medicalappointments.specialization.specialization.DTO.SpecializationDTO;
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
    ResponseEntity<Page<SpecializationDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<SpecializationDTO> readById(@PathVariable Integer id){
        return service.readById(id);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<SpecializationDTO> readBySpecialization(@RequestParam String specialization){
        return service.readBySpecialization(specialization);
    }

    @PostMapping
    ResponseEntity<SpecializationDTO> createSpecialization(@Valid @RequestBody Specialization specialization){
        return service.createSpecialization(specialization);
    }

    @PutMapping("/{id}")
    ResponseEntity<SpecializationDTO> updateSpecialization(@PathVariable Integer id, @Valid @RequestBody SpecializationDTO specialization){
        return service.updateSpecialization(id, specialization);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSpecialization(@PathVariable Integer id){
        return service.deleteSpecialization(id);
    }
}
