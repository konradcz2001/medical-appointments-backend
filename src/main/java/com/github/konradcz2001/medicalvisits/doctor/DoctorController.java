package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
class DoctorController {
    private final DoctorRepository repository;
    private final DoctorFacade facade;

    DoctorController(final DoctorRepository repository, final DoctorFacade facade) {
        this.repository = repository;
        this.facade = facade;
    }

    @GetMapping
    ResponseEntity<List<Doctor>> readAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> readById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    ResponseEntity<List<Doctor>> readAllByName(@RequestParam String name){
        List<Doctor> doctors = repository.findAllByName(name);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "surname")
    ResponseEntity<List<Doctor>> readAllBySurname(@RequestParam String surname){
        List<Doctor> doctors = repository.findAllBySurname(surname);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<List<Doctor>> readAllBySpecialization(@RequestParam String specialization){
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

    @PatchMapping("/{id}/add-leave")
    ResponseEntity<?> addLeave(@PathVariable long id, @RequestBody Leave leave){
        return repository.findById(id)
                .map(doctor -> facade.addLeave(leave, doctor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/remove-leave")
    ResponseEntity<?> removeLeave(@PathVariable long id, @RequestBody Leave leave){
        return repository.findById(id)
                .map(doctor -> facade.removeLeave(leave, doctor))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/leaves")
    ResponseEntity<Set<Leave>> readAllLeaves(@PathVariable long id){
        return repository.findById(id)
                .map(Doctor::getLeaves)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/available", params = "date")
    ResponseEntity<List<Doctor>> readAllAvailableByDate(@RequestParam String date){
        LocalDateTime d = LocalDateTime.parse(date);
        return facade.readAllAvailableByDate(d);
    }

}
