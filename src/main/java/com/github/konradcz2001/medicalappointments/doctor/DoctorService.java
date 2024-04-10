package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveFacade;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationFacade;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongLeaveException;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

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
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
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
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<?> addLeave(Long id, Leave leave){

        return repository.findById(id)
                .map(doctor ->{


                    if(leave.getEndDate().isBefore(LocalDateTime.now()))
                        throw new WrongLeaveException("The leave is over");

                    if(leave.getStartDate().isAfter(leave.getEndDate()))
                        throw new WrongLeaveException("The beginning of the leave cannot be later than the end");

                    leave.setId(null);

                    doctor.getLeaves().forEach(doctorLeave -> {
                        var ls = leave.getStartDate();
                        var le = leave.getEndDate();
                        var dls = doctorLeave.getStartDate();
                        var dle = doctorLeave.getEndDate();


                        // dls ls le dle
                        if((ls.isAfter(dls) || ls.isEqual(dls)) && (le.isBefore(dle) || le.isEqual(dle))){
                            throw new WrongLeaveException("Leave for the given period of time already exists");
                        }
                        // ls dls dle le
                        else if((ls.isBefore(dls) || ls.isEqual(dls)) && (le.isAfter(dle) || le.isEqual(dle))){
                            doctorLeave.setStartDate(leave.getStartDate());
                            doctorLeave.setEndDate(leave.getEndDate());
                            leaveFacade.save(doctorLeave);
                        }
                        // ls dls le dle
                        else if((ls.isBefore(dls) || ls.isEqual(dls)) && (le.isBefore(dle) || le.isEqual(dle)) && (le.isAfter(dls) || le.isEqual(dls))){
                            if(dls.isBefore(LocalDateTime.now()))
                                throw new WrongLeaveException("Leave for the given period of time already exists");

                            doctorLeave.setStartDate(leave.getStartDate());
                            leaveFacade.save(doctorLeave);
                        }
                        // dls ls dle le
                        else if((ls.isAfter(dls) || ls.isEqual(dls)) && (le.isAfter(dle) || le.isEqual(dle)) && (dle.isAfter(ls) || dle.isEqual(ls))){
                            doctorLeave.setEndDate(leave.getEndDate());
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
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));

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

                     throw new WrongLeaveException("Doctor with id " + doctorId + " does not have the specified leave with id " + leaveId);
                }).orElseThrow(() -> new ResourceNotFoundException(LEAVE, leaveId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

    }

    ResponseEntity<?> removeSpecialization(Long doctorId, Integer specializationId){
        return repository.findById(doctorId)
                .map(doctor -> specializationFacade.findById(specializationId)
                        .map(specialization -> {
                            doctor.removeSpecialization(specialization);
                            repository.save(doctor);
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, specializationId.longValue())))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    ResponseEntity<Page<Doctor>> readAllAvailableByDate(LocalDateTime date, Pageable pageable){
        Page<Doctor> doctors = repository.findAll(pageable)
                .map(doctor -> isAvailableByDate(date, doctor) ? doctor : null);

        if(doctors.isEmpty())
            throw new EmptyPageException();
        return ResponseEntity.ok(doctors);
    }

    boolean isAvailableByDate(LocalDateTime date, Doctor doctor){
        return doctor.getLeaves().stream()
                .allMatch(leave -> (leave.getStartDate().isAfter(date) || leave.getEndDate().isBefore(date)));
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
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<Page<Leave>> readAllLeaves(Long id, Pageable pageable){
        return returnResponse(() -> leaveFacade.findAllByDoctorId(id, pageable));
    }

    ResponseEntity<Set<Specialization>> readAllSpecializations(Long id){
        return repository.findById(id)
                .map(Doctor::getSpecializations)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<?> addSpecializations(Long doctorId, Set<Integer> specializationIds) {

        return repository.findById(doctorId)
                .map(doctor -> {
                    specializationIds.forEach(id -> specializationFacade.findById(id)
                            .ifPresentOrElse(doctor::addSpecialization,
                                    () -> {throw new ResourceNotFoundException(SPECIALIZATION, id.longValue());}
                            ));

                    repository.save(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

    }

    ResponseEntity<Page<Review>> readAllReviews(Long id, Pageable pageable) {
        return returnResponse(() -> reviewFacade.findAllByDoctorId(id, pageable));
    }
}
