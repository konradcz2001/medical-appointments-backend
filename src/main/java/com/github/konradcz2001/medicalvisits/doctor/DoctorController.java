package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors")
class DoctorController {
    private final DoctorRepository repository;

    DoctorController(final DoctorRepository customerRepository) {
        this.repository = customerRepository;
    }

    @GetMapping
    ResponseEntity<List<Doctor>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> findById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    ResponseEntity<List<Doctor>> findAllByName(@RequestParam String name){
        List<Doctor> doctors = repository.findAllByName(name);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "surname")
    ResponseEntity<List<Doctor>> findAllBySurname(@RequestParam String surname){
        List<Doctor> doctors = repository.findAllBySurname(surname);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<List<Doctor>> findAllBySpecialization(@RequestParam String specialization){
        List<Doctor> doctors = repository.findAllBySpecialization(specialization);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor){
        Doctor result = repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping
    ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor){
        if(repository.existsById(doctor.getId())){
            repository.save(doctor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
