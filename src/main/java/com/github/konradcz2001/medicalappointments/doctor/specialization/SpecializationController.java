package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/doctors/specializations")
@CrossOrigin
class SpecializationController {
    private final SpecializationRepository repository;

    SpecializationController(final SpecializationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<Page<Specialization>> readAll(Pageable pageable){
        Page<Specialization> all = repository.findAll(pageable);
        if(all.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    ResponseEntity<Specialization> readById(@PathVariable int id){
        return repository.findById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Specialization> readBySpecialization(@RequestParam String specialization){
        return repository.findFirstBySpecialization(specialization)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<?> addSpecialization(@RequestBody Specialization specialization){
        if(repository.existsBySpecialization(specialization.getSpecialization()))
            return ResponseEntity.badRequest().body("Such specialization already exist");

        Specialization result = repository.save(specialization);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateSpecialization(@PathVariable int id, @RequestBody Specialization specialization){
        if(repository.existsById(id)){
            specialization.setId(id);
            repository.save(specialization);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSpecialization(@PathVariable int id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
