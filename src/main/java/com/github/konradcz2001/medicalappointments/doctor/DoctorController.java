package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
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
    private final DoctorRepository repository;
    private final DoctorFacade facade;

    DoctorController(final DoctorRepository repository, final DoctorFacade facade) {
        this.repository = repository;
        this.facade = facade;
    }

    @GetMapping
    ResponseEntity<Page<Doctor>> readAll(Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> readById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "first-name")
    ResponseEntity<Page<Doctor>> readAllByName(@RequestParam String name, Pageable pageable){
        Page<Doctor> doctors = repository.findAllByFirstNameContainingIgnoreCase(name, pageable);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "last-name")
    ResponseEntity<Page<Doctor>> readAllBySurname(@RequestParam String lastName, Pageable pageable){
        Page<Doctor> doctors = repository.findAllByLastNameContainingIgnoreCase(lastName, pageable);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "specialization")
    ResponseEntity<Page<Doctor>> readAllBySpecialization(@RequestParam String specialization, Pageable pageable){
        Page<Doctor> doctors = repository.findAllByAnySpecializationContainingIgnoreCase(specialization, pageable);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor){
        return facade.addDoctor(doctor);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateDoctor(@PathVariable long id, @RequestBody Doctor doctor){
        if(repository.existsById(id)){
            doctor.setId(id);
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

    @PatchMapping("/{id}/add-specializations")
    ResponseEntity<?> addSpecializations(@PathVariable long id, @RequestBody Set<Integer> specializationIds){
        return repository.findById(id)
                .map(doctor -> {
                    facade.addSpecializations(doctor, specializationIds);
                    return ResponseEntity.noContent().build();
                })
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
