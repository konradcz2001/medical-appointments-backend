package com.github.konradcz2001.medicalappointments.doctor.leave;

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


    ResponseEntity<Page<Leave>> readAllByStartAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartAfter(after, pageable));
    }



    ResponseEntity<Page<Leave>> readAllByEndAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndAfter(after, pageable));
    }


    ResponseEntity<Page<Leave>> readAllByStartBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartBefore(before, pageable));
    }


    ResponseEntity<Page<Leave>> readAllByEndBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndBefore(before, pageable));
    }


    ResponseEntity<Page<Leave>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartAfterAndEndBefore(after, before, pageable));
    }

}
