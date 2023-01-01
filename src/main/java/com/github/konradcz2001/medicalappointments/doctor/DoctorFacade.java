package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorFacade {
    private final DoctorRepository repository;
    private final LeaveRepository leaveRepository;
    private final SpecializationRepository specializationRepository;

    DoctorFacade(final DoctorRepository repository, final LeaveRepository leaveRepository, final SpecializationRepository specializationRepository) {
        this.repository = repository;
        this.leaveRepository = leaveRepository;
        this.specializationRepository = specializationRepository;
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

    ResponseEntity<Doctor> addDoctor(Doctor doctor){

        if(!doctor.getSpecializations().isEmpty()) {
            boolean someSpecializationIsIncorrect = doctor.getSpecializations().stream().anyMatch(spec ->
                    !specializationRepository.existsBySpecialization(spec.getSpecialization()));

            if (someSpecializationIsIncorrect)
                return ResponseEntity.badRequest().build();
        }

        Doctor result = repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

}
