package com.github.konradcz2001.medicalappointments.leave;

import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTOMapper;
import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTO;
import com.github.konradcz2001.medicalappointments.exception.MessageType;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;

@Service
class LeaveService {
    private final LeaveRepository repository;
    private final LeaveDTOMapper dtoMapper;

    LeaveService(final LeaveRepository repository, LeaveDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }


    ResponseEntity<Page<LeaveDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    ResponseEntity<LeaveDTO> readById(Long id) {
        return  repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.LEAVE, id));
    }


    ResponseEntity<Page<LeaveDTO>> readAllByStartAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfter(after, pageable), dtoMapper);
    }



    ResponseEntity<Page<LeaveDTO>> readAllByEndAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateAfter(after, pageable), dtoMapper);
    }


    ResponseEntity<Page<LeaveDTO>> readAllByStartBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateBefore(before, pageable), dtoMapper);
    }


    ResponseEntity<Page<LeaveDTO>> readAllByEndBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateBefore(before, pageable), dtoMapper);
    }


    ResponseEntity<Page<LeaveDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfterAndEndDateBefore(after, before, pageable), dtoMapper);

    }

}
