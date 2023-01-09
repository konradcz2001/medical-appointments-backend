package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public Optional<Doctor> findById(final Long id) {
        return repository.findById(id);
    }

    void addLeave(Leave leave, Doctor doctor){

        if(leave.getTillWhen().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The leave is over");

        if(leave.getSinceWhen().isAfter(leave.getTillWhen()))
            throw new IllegalArgumentException("The beginning of the leave cannot be later than the end");

        leave.setId(null);

        doctor.getLeaves().forEach(doctorLeave -> {
            var ls = leave.getSinceWhen();
            var lt = leave.getTillWhen();
            var dls = doctorLeave.getSinceWhen();
            var dlt = doctorLeave.getTillWhen();

            // < - beginning of leave, > - end of leave, ( - beginning of doctorLeave, ) - end of doctorLeave
            // (<   >)
            if((ls.isAfter(dls) || ls.isEqual(dls)) && (lt.isBefore(dlt) || lt.isEqual(dlt))){
                throw new IllegalArgumentException("Leave for the given period of time already exists");
            }
            // <(   )>
            else if((ls.isBefore(dls) || ls.isEqual(dls)) && (lt.isAfter(dlt) || lt.isEqual(dlt))){
                doctorLeave.setSinceWhen(leave.getSinceWhen());
                doctorLeave.setTillWhen(leave.getTillWhen());
                leaveRepo.save(doctorLeave);
            }
            // <(   >)
            else if((ls.isBefore(dls) || ls.isEqual(dls)) && (lt.isBefore(dlt) || lt.isEqual(dlt)) && (lt.isAfter(dls) || lt.isEqual(dls))){
                if(dls.isBefore(LocalDateTime.now()))
                    throw new IllegalArgumentException("Leave for the given period of time already exists");

                doctorLeave.setSinceWhen(leave.getSinceWhen());
                leaveRepo.save(doctorLeave);
            }
            // (<   )>
            else if((ls.isAfter(dls) || ls.isEqual(dls)) && (lt.isAfter(dlt) || lt.isEqual(dlt)) && (dlt.isAfter(ls) || dlt.isEqual(ls))){
                doctorLeave.setTillWhen(leave.getTillWhen());
                leaveRepo.save(doctorLeave);
            }
            // ( ) < > || < > ( )
            else{
                leave.setDoctor(doctor);
                leaveRepo.save(leave);
            }
        });

        if(doctor.getLeaves().isEmpty()){
            leave.setDoctor(doctor);
            leaveRepo.save(leave);
        }
    }

    void removeLeave(Long id, Doctor doctor){
        Optional<Leave> leave = leaveRepo.findById(id).map(l -> {
            boolean noneMatch = doctor.getLeaves().stream().noneMatch(doctorLeave -> doctorLeave.getId().equals(id));
            if(noneMatch)
                throw new IllegalArgumentException("Doctor has no leave with id = " + id);
            return l;
        });

        if(leave.isEmpty())
            throw new IllegalArgumentException("Leave with id = " + id + " does not exist");

        doctor.removeLeave(leave.get());
        leaveRepo.delete(leave.get());
    }

    void removeSpecialization(Integer id, Doctor doctor){
        var specialization = specializationRepo.findById(id);
        if(specialization.isEmpty())
            throw new IllegalArgumentException("Specialization with id = " + id + " does not exist");

        doctor.removeSpecialization(specialization.get());
        repository.save(doctor);
    }

    List<Doctor> readAllAvailableByDate(LocalDateTime date){
        return repository.findAll().stream()
                .filter(doctor -> isAvailableByDate(date, doctor))
                .collect(Collectors.toList());
    }

    boolean isAvailableByDate(LocalDateTime date, Doctor doctor){
        return doctor.getLeaves().stream()
                .allMatch(leave -> (leave.getSinceWhen().isAfter(date) || leave.getTillWhen().isBefore(date)));
    }

    Doctor addDoctor(Doctor doctor){
        doctor.setId(null);
        doctor.setSpecializations(new HashSet<>());
        doctor.setLeaves(new HashSet<>());
        return repository.save(doctor);
    }

    Doctor updateDoctor(Long id, Doctor doctor, Doctor toUpdate){
        toUpdate.setId(id);
        toUpdate.setSpecializations(doctor.getSpecializations());
        toUpdate.setLeaves(doctor.getLeaves());
        return repository.save(toUpdate);
    }

    void addSpecializations(final Doctor doctor, final Set<Integer> specializationIds) {
        specializationIds.forEach(id -> {
            var spec = specializationRepo.findById(id);
            if(spec.isEmpty())
                throw new IllegalArgumentException("Specialization with id = " + id + " does not exist");
            doctor.addSpecialization(spec.get());
        });
        repository.save(doctor);
    }

}
