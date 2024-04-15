package com.github.konradcz2001.medicalappointments.doctor.leave;

import com.github.konradcz2001.medicalappointments.doctor.leave.DTO.LeaveDTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.leave.DTO.LeaveResponseDTO;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.MessageType;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
class LeaveService {
    LeaveRepository repository;

    LeaveService(final LeaveRepository repository) {
        this.repository = repository;
    }

    private ResponseEntity<Page<LeaveResponseDTO>> returnResponse(Supplier<Page<Leave>> suppliedLeaves) {
        var leaves = suppliedLeaves.get()
                .map(LeaveDTOMapper::apply);
        if(leaves.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(leaves);
    }

    ResponseEntity<Page<LeaveResponseDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<LeaveResponseDTO> readById(Long id) {
        return  repository.findById(id)
                .map(LeaveDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.LEAVE, id));
    }


    ResponseEntity<Page<LeaveResponseDTO>> readAllByStartAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfter(after, pageable));
    }



    ResponseEntity<Page<LeaveResponseDTO>> readAllByEndAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateAfter(after, pageable));
    }


    ResponseEntity<Page<LeaveResponseDTO>> readAllByStartBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateBefore(before, pageable));
    }


    ResponseEntity<Page<LeaveResponseDTO>> readAllByEndBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateBefore(before, pageable));
    }


    ResponseEntity<Page<LeaveResponseDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfterAndEndDateBefore(after, before, pageable));

    }

}
