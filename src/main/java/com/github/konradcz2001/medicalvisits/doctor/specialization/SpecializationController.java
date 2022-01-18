package com.github.konradcz2001.medicalvisits.doctor.specialization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors/specializations")
@CrossOrigin
class SpecializationController {
    private final SpecializationRepository repository;

    SpecializationController(final SpecializationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<Specialization>> readAll(){
        List<Specialization> all = repository.findAll();
        if(all.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    ResponseEntity<Specialization> readById(@PathVariable int id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Specialization> readBySpecialization(@RequestParam String specialization){
        Specialization s = repository.findFirstBySpecialization(specialization);
        if(s == null)
            return ResponseEntity.notFound().build();
        return  ResponseEntity.ok(s);
    }

    @PostMapping
    ResponseEntity<?> addSpecialization(@RequestBody Specialization specialization){
        List<Specialization> specializations = repository.findAll();
        if(specializations.contains(specialization))
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
