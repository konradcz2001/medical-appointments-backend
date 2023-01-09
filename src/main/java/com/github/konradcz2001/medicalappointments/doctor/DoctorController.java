package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity<Page<Doctor>> readAll(Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> readById(@PathVariable Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "first-name")
    ResponseEntity<Page<Doctor>> readAllByName(@RequestParam(name = "first-name") String firstName, Pageable pageable){
        Page<Doctor> doctors = repository.findAllByFirstNameContainingIgnoreCase(firstName, pageable);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping(params = "last-name")
    ResponseEntity<Page<Doctor>> readAllBySurname(@RequestParam(name = "last-name") String lastName, Pageable pageable){
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
        Doctor result = facade.addDoctor(doctor);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor toUpdate){
        return repository.findById(id)
                .map(doctor -> ResponseEntity.ok(facade.updateDoctor(id, doctor, toUpdate)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDoctor(@PathVariable Long id){
        return repository.findById(id)
                .map(doctor -> {
                    //doctor.removeSpecialization();
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/add-leave")
    ResponseEntity<?> addLeave(@PathVariable Long id, @RequestBody Leave leave){
        return repository.findById(id)
                .map(doctor ->{
                    facade.addLeave(leave, doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/add-specializations")
    ResponseEntity<?> addSpecializations(@PathVariable Long id, @RequestBody Set<Integer> specializationIds){
        return repository.findById(id)
                .map(doctor -> {
                    facade.addSpecializations(doctor, specializationIds);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/{doctorId}/remove-leave", params = "id")
    ResponseEntity<?> removeLeave(@PathVariable Long doctorId, @RequestParam Long id){
        return repository.findById(doctorId)
                .map(doctor -> {
                    facade.removeLeave(id, doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/{doctorId}/remove-specialization", params = "id")
    ResponseEntity<?> removeSpecialization(@PathVariable Long doctorId, @RequestParam Integer id){
        return repository.findById(doctorId)
                .map(doctor -> {
                    facade.removeSpecialization(id, doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/leaves")
    ResponseEntity<Set<Leave>> readAllLeaves(@PathVariable Long id){
        return repository.findById(id)
                .map(Doctor::getLeaves)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/specializations")
    ResponseEntity<Set<Specialization>> readAllSpecializations(@PathVariable Long id){
        return repository.findById(id)
                .map(Doctor::getSpecializations)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/available", params = "date")
    ResponseEntity<List<Doctor>> readAllAvailableByDate(@RequestParam LocalDateTime date){
        List<Doctor> doctors = facade.readAllAvailableByDate(date);
        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(doctors);
    }

}
