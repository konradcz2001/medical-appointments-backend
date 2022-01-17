package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
