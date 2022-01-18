package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorFacade {
    private final DoctorRepository repository;
    private final LeaveRepository leaveRepository;

    DoctorFacade(final DoctorRepository repository, final LeaveRepository leaveRepository) {
        this.repository = repository;
        this.leaveRepository = leaveRepository;
    }

    public Optional<Doctor> findById(final long id) {
        return repository.findById(id);
    }

    ResponseEntity<?> addLeave(Leave leave, Doctor doctor){
        if(doctor.getLeaves().contains(leave))
            return ResponseEntity.badRequest().body("Such leave already exist");

        doctor.addLeave(leave);
        leaveRepository.save(leave);
        return ResponseEntity.noContent().build();
    }

    ResponseEntity<?> removeLeave(Leave leave, Doctor doctor){
        if(!doctor.getLeaves().contains(leave))
            return ResponseEntity.badRequest().body("Such leave does not exist");

        doctor.removeLeave(leave);
        leaveRepository.delete(leave);
        return ResponseEntity.noContent().build();
    }

    ResponseEntity<List<Doctor>> readAllAvailableByDate(LocalDateTime date){
        List<Doctor> doctors = repository.findAll().stream()
                .filter(doctor -> isAvailableByDate(date, doctor))
                .collect(Collectors.toList());

        if(doctors.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(doctors);
    }

    boolean isAvailableByDate(LocalDateTime date, Doctor doctor){
        return doctor.getLeaves().stream()
                .filter(leave -> (date.isAfter(leave.getSinceWhen()) && date.isBefore(leave.getTillWhen())))
                .findFirst()
                .isEmpty();
    }

}
