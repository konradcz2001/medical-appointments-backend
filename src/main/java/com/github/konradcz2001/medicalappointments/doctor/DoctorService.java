package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveFacade;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationFacade;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;

@Service
class DoctorService {
    private final DoctorRepository repository;
    private final SpecializationFacade specializationFacade;
    private final ReviewFacade reviewFacade;
    private final LeaveFacade leaveFacade;

    DoctorService(final DoctorRepository repository, SpecializationFacade specializationFacade, final ReviewFacade reviewFacade, LeaveFacade leaveFacade) {
        this.repository = repository;
        this.specializationFacade = specializationFacade;
        this.reviewFacade = reviewFacade;
        this.leaveFacade = leaveFacade;
    }


    ResponseEntity<Page<Doctor>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<Doctor> readById(Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<Page<Doctor>> readAllByFirstName(String firstName, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContainingIgnoreCase(firstName, pageable));
    }

    ResponseEntity<Page<Doctor>> readAllByLastName(String lastName, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContainingIgnoreCase(lastName, pageable));
    }

    ResponseEntity<Page<Doctor>> readAllBySpecialization(String specialization, Pageable pageable){
        return returnResponse(() -> repository.findAllByAnySpecializationContainingIgnoreCase(specialization, pageable));
    }

    ResponseEntity<?> deleteDoctor(Long id){
        return repository.findById(id)
                .map(doctor -> {
                    //TODO is specialization, reviews or leaves removed?
                    //doctor.removeSpecialization();
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<?> addLeave(Long id, Leave leave){

        return repository.findById(id)
                .map(doctor ->{


                    if(leave.getEnd().isBefore(LocalDateTime.now()))
                        throw new IllegalArgumentException("The leave is over");

                    if(leave.getStart().isAfter(leave.getEnd()))
                        throw new IllegalArgumentException("The beginning of the leave cannot be later than the end");

                    leave.setId(null);

                    doctor.getLeaves().forEach(doctorLeave -> {
                        var ls = leave.getStart();
                        var le = leave.getEnd();
                        var dls = doctorLeave.getStart();
                        var dle = doctorLeave.getEnd();


                        // dls ls le dle
                        if((ls.isAfter(dls) || ls.isEqual(dls)) && (le.isBefore(dle) || le.isEqual(dle))){
                            throw new IllegalArgumentException("Leave for the given period of time already exists");
                        }
                        // ls dls dle le
                        else if((ls.isBefore(dls) || ls.isEqual(dls)) && (le.isAfter(dle) || le.isEqual(dle))){
                            doctorLeave.setStart(leave.getStart());
                            doctorLeave.setEnd(leave.getEnd());
                            leaveFacade.save(doctorLeave);
                        }
                        // ls dls le dle
                        else if((ls.isBefore(dls) || ls.isEqual(dls)) && (le.isBefore(dle) || le.isEqual(dle)) && (le.isAfter(dls) || le.isEqual(dls))){
                            if(dls.isBefore(LocalDateTime.now()))
                                throw new IllegalArgumentException("Leave for the given period of time already exists");

                            doctorLeave.setStart(leave.getStart());
                            leaveFacade.save(doctorLeave);
                        }
                        // dls ls dle le
                        else if((ls.isAfter(dls) || ls.isEqual(dls)) && (le.isAfter(dle) || le.isEqual(dle)) && (dle.isAfter(ls) || dle.isEqual(ls))){
                            doctorLeave.setEnd(leave.getEnd());
                            leaveFacade.save(doctorLeave);
                        }
                        // ls le dls dle || dls dle ls le
                        else{
                            leave.setDoctor(doctor);
                            leaveFacade.save(leave);
                        }
                    });

                    if(doctor.getLeaves().isEmpty()){
                        leave.setDoctor(doctor);
                        leaveFacade.save(leave);
                    }

                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());

    }

    ResponseEntity<?> removeLeave(Long doctorId, Long leaveId){
        return repository.findById(doctorId)
                .map(doctor -> leaveFacade.findById(leaveId).map(leave -> {
                    boolean anyMatch = doctor.getLeaves().stream().anyMatch(doctorLeave -> doctorLeave.getId().equals(leaveId));
                    if(anyMatch){
                        doctor.removeLeave(leave);
                        repository.save(doctor);
                        return ResponseEntity.noContent().build();
                    }

                     return ResponseEntity.notFound().build();
                })
                .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());

    }

    ResponseEntity<?> removeSpecialization(Long doctorId, Integer specializationId){
        return repository.findById(doctorId)
                .map(doctor -> specializationFacade.findById(specializationId)
                        .map(specialization -> {
                            doctor.removeSpecialization(specialization);
                            repository.save(doctor);
                            return ResponseEntity.noContent().build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
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
                .allMatch(leave -> (leave.getStart().isAfter(date) || leave.getEnd().isBefore(date)));
    }

    ResponseEntity<?> createDoctor(Doctor doctor){
        doctor.setId(null);
        //TODO setSpecjalizations and setLeaves is ok in addDoctor?
        doctor.setSpecializations(new HashSet<>());
        doctor.setLeaves(new HashSet<>());
        Doctor result =  repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    ResponseEntity<?> updateDoctor(Long id, Doctor toUpdate){
        return repository.findById(id)
                .map(doctor -> {
                    toUpdate.setId(id);
                    //TODO setSpecjalizations and setLeaves is ok in updateDoctor?
                    toUpdate.setSpecializations(doctor.getSpecializations());
                    toUpdate.setLeaves(doctor.getLeaves());
                    return ResponseEntity.ok(repository.save(toUpdate));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<Page<Leave>> readAllLeaves(Long id, Pageable pageable){
        return returnResponse(() -> leaveFacade.findAllByDoctorId(id, pageable));
    }

    ResponseEntity<Set<Specialization>> readAllSpecializations(Long id){
        return repository.findById(id)
                .map(Doctor::getSpecializations)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<?> addSpecializations(Long doctorId, Set<Integer> specializationIds) {

        return repository.findById(doctorId)
                .map(doctor -> {
                    specializationIds.forEach(id -> specializationFacade.findById(id)
                            .ifPresentOrElse(doctor::addSpecialization,
                                    () -> {throw new IllegalArgumentException("Specialization with id = " + id + " does not exist");}
                            ));

                    repository.save(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<Page<Review>> readAllReviews(Long id, Pageable pageable) {
        return returnResponse(() -> reviewFacade.findAllByDoctorId(id, pageable));
    }
}
