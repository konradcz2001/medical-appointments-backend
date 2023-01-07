package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DoctorFacade {
    private final DoctorRepository repository;
    private final LeaveRepository leaveRepo;
    private final SpecializationRepository specializationRepo;

    DoctorFacade(final DoctorRepository repository, final LeaveRepository leaveRepo, final SpecializationRepository specializationRepo) {
        this.repository = repository;
        this.leaveRepo = leaveRepo;
        this.specializationRepo = specializationRepo;
    }

    public Optional<Doctor> findById(final long id) {
        return repository.findById(id);
    }

    ResponseEntity<?> addLeave(Leave leave, Doctor doctor){
        if(doctor.getLeaves().contains(leave))
            return ResponseEntity.badRequest().body("Such leave already exist");

        leave.setDoctor(doctor);
        leaveRepo.save(leave);
        return ResponseEntity.noContent().build();
    }

    ResponseEntity<?> removeLeave(Leave leave, Doctor doctor){
        if(!doctor.getLeaves().contains(leave))
            return ResponseEntity.badRequest().body("Such leave does not exist");

        doctor.removeLeave(leave);
        leaveRepo.delete(leave);
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
                    !specializationRepo.existsBySpecialization(spec.getSpecialization()));

            if (someSpecializationIsIncorrect)
                return ResponseEntity.badRequest().build();
        }

        Doctor result = repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    void addSpecializations(final Doctor doctor, final Set<Integer> specializationIds) {
        specializationIds.forEach(id -> {
            var spec = specializationRepo.findById(id);
            if(spec.isEmpty())
                throw new IllegalArgumentException("There is no such specialization");
            doctor.addSpecialization(spec.get());
        });
        repository.save(doctor);
    }

}
