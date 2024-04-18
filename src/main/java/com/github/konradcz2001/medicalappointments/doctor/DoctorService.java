package com.github.konradcz2001.medicalappointments.doctor;


import com.github.konradcz2001.medicalappointments.doctor.DTO.*;
import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import com.github.konradcz2001.medicalappointments.leave.leave.Leave;
import com.github.konradcz2001.medicalappointments.leave.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.specialization.specialization.SpecializationRepository;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongLeaveException;
import com.github.konradcz2001.medicalappointments.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

@Service
class DoctorService {
    private final DoctorRepository repository;
    private final SpecializationRepository specializationRepository;
    private final ReviewRepository reviewRepository;
    private final LeaveRepository leaveRepository;
    private final DoctorDTOMapper dtoMapper;

    DoctorService(final DoctorRepository repository, final SpecializationRepository specializationRepository,
                  final ReviewRepository reviewRepository, final LeaveRepository leaveRepository, DoctorDTOMapper dtoMapper) {
        this.repository = repository;
        this.specializationRepository = specializationRepository;
        this.reviewRepository = reviewRepository;
        this.leaveRepository = leaveRepository;
        this.dtoMapper = dtoMapper;
    }


    ResponseEntity<Page<DoctorDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    ResponseEntity<DoctorDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<Page<DoctorDTO>> readAllByFirstName(String firstName, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContainingIgnoreCase(firstName, pageable), dtoMapper);
    }

    ResponseEntity<Page<DoctorDTO>> readAllByLastName(String lastName, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContainingIgnoreCase(lastName, pageable), dtoMapper);
    }

    ResponseEntity<Page<DoctorDTO>> readAllBySpecialization(String specialization, Pageable pageable){
        return returnResponse(() -> repository.findAllByAnySpecializationContainingIgnoreCase(specialization, pageable), dtoMapper);
    }

    ResponseEntity<?> deleteDoctor(Long id){
        return repository.findById(id)
                .map(doctor -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }


    ResponseEntity<?> addLeave(Long id, Leave leave) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));

        if (leave.getStartDate().isAfter(leave.getEndDate()))
            throw new WrongLeaveException("The beginning of the leave cannot be later than the end");

        leave.setId(null);

        List<Leave> leavesToRemove = new ArrayList<>();


        for (Leave existingLeave : doctor.getLeaves()) {
            if (!(leave.getEndDate().isBefore(existingLeave.getStartDate()) || leave.getStartDate().isAfter(existingLeave.getEndDate()))) {

                leave.setStartDate(leave.getStartDate().isBefore(existingLeave.getStartDate()) ? leave.getStartDate() : existingLeave.getStartDate());
                leave.setEndDate(leave.getEndDate().isAfter(existingLeave.getEndDate()) ? leave.getEndDate() : existingLeave.getEndDate());

                leavesToRemove.add(existingLeave);
            }
        }

        doctor.getLeaves().removeAll(leavesToRemove);
        doctor.addLeave(leave);

        repository.save(doctor);

        return ResponseEntity.noContent().build();
    }


    ResponseEntity<?> removeLeave(Long doctorId, Long leaveId){
        return repository.findById(doctorId)
                .map(doctor -> doctor.getLeaves().stream()
                        .filter(leave -> leave.getId().equals(leaveId)).findAny()
                        .map(leave -> {
                            doctor.removeLeave(leave);
                            repository.save(doctor);
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongLeaveException("Doctor with id = " + doctorId + " does not have the specified leave with id = " + leaveId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

    }

    ResponseEntity<?> removeSpecialization(Long doctorId, Integer specializationId){
        return repository.findById(doctorId)
                .map(doctor -> doctor.getSpecializations().stream()
                        .filter(spec -> spec.getId().equals(specializationId)).findAny()
                        .map(spec ->{
                              doctor.removeSpecialization(spec);
                              repository.save(doctor);
                              return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongSpecializationException("Doctor with id = " + doctorId + " does not have the specified specialization with id = " + specializationId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    ResponseEntity<Page<DoctorDTO>> readAllAvailableByDate(LocalDateTime date, Pageable pageable){
        return returnResponse(() -> repository.findAllAvailableByDate(date, pageable), dtoMapper);
    }

//    private boolean isAvailableByDate(LocalDateTime date, Doctor doctor){
//        return doctor.getLeaves().stream()
//                .allMatch(leave -> (leave.getStartDate().isAfter(date) || leave.getEndDate().isBefore(date)));
//    }

    ResponseEntity<DoctorDTO> createDoctor(Doctor doctor){
        doctor.setId(null);
        doctor.setSpecializations(new HashSet<>());
        doctor.setLeaves(new ArrayList<>());
        doctor.setReviews(new ArrayList<>());
        Doctor created =  repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }

    ResponseEntity<DoctorDTO> updateDoctor(Long id, DoctorDTO toUpdate){
        return repository.findById(id)
                .map(doctor -> {
                    Doctor updated = repository.save(dtoMapper.mapFromDTO(toUpdate, doctor));
                    return ResponseEntity.ok(dtoMapper.mapToDTO(updated));
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<Page<DoctorLeaveDTO>> readAllLeaves(Long id, Pageable pageable){
        var doctors = leaveRepository.findAllByDoctorId(id, pageable)
                .map(dtoMapper::applyForLeave);

        if(doctors.isEmpty())
            throw new EmptyPageException();
        return ResponseEntity.ok(doctors);
    }

    ResponseEntity<Set<DoctorSpecializationDTO>> readAllSpecializations(Long id){
        return repository.findById(id)
                .map(Doctor::getSpecializations)
                .map(spec -> spec.stream().map(dtoMapper::applyForSpecialization)
                        .collect(Collectors.toSet()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    ResponseEntity<?> addSpecializations(Long doctorId, Set<Integer> specializationIds) {

        return repository.findById(doctorId)
                .map(doctor -> {
                    if((doctor.getSpecializations().size() + specializationIds.size()) > 5)
                        throw new WrongSpecializationException("Doctor with id = " + doctorId + " can not have more than 5 specializations, he has already " + specializationIds.size() + " specializations and in request there are next " + specializationIds.size() + " specializations");
                    specializationIds.forEach(id -> specializationRepository.findById(id)
                            .ifPresentOrElse(doctor::addSpecialization,
                                    () -> {throw new ResourceNotFoundException(SPECIALIZATION, id.longValue());}
                            ));

                    repository.save(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

    }

    ResponseEntity<Page<DoctorReviewDTO>> readAllReviews(Long id, Pageable pageable) {
        var doctors = reviewRepository.findAllByDoctorId(id, pageable)
                .map(dtoMapper::applyForReview);
        if(doctors.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(doctors);
    }
}
