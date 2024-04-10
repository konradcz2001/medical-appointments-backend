package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.github.konradcz2001.medicalappointments.exception.MessageType;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;

@Service
class LeaveService {
    LeaveRepository repository;

    LeaveService(final LeaveRepository repository) {
        this.repository = repository;
    }


    ResponseEntity<Page<Leave>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<Leave> readById(Long id) {
        return  repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.LEAVE, id));
    }


    ResponseEntity<Page<Leave>> readAllByStartAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfter(after, pageable));
    }



    ResponseEntity<Page<Leave>> readAllByEndAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateAfter(after, pageable));
    }


    ResponseEntity<Page<Leave>> readAllByStartBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateBefore(before, pageable));
    }


    ResponseEntity<Page<Leave>> readAllByEndBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateBefore(before, pageable));
    }


    ResponseEntity<Page<Leave>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfterAndEndDateBefore(after, before, pageable));
    }

}
