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

/**
 * This code snippet represents a class named LeaveService.
 * It is a service class that handles operations related to leaves.
 * The class has a constructor that takes a LeaveRepository object and a LeaveDTOMapper object as parameters.
 * The constructor initializes the repository and dtoMapper fields with the supplied objects.
 * The class provides methods for performing operations on leaves, such as retrieving leaves, mapping leaves to DTOs, and handling exceptions.
 */
@Service
class LeaveService {
    private final LeaveRepository repository;
    private final LeaveDTOMapper dtoMapper;

    LeaveService(final LeaveRepository repository, LeaveDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Retrieves all leaves with pagination.
     *
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    /**
     * Retrieves a leave with the specified ID.
     *
     * @param id The ID of the leave to retrieve.
     * @return A ResponseEntity object containing the LeaveDTO representing the leave.
     * @throws ResourceNotFoundException If the leave with the specified ID is not found.
     */
    ResponseEntity<LeaveDTO> readById(Long id) {
        return  repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.LEAVE, id));
    }

    /**
     * Retrieves all leaves with a start date after the specified date.
     *
     * @param after    The start date (exclusive) to filter the leaves.
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the filtered leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAllByStartAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfter(after, pageable), dtoMapper);
    }


    /**
     * Retrieves all leaves with an end date after the specified date.
     *
     * @param after    The end date (exclusive) to filter the leaves.
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the filtered leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAllByEndAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateAfter(after, pageable), dtoMapper);
    }

    /**
     * Retrieves all leaves with a start date before the specified date.
     *
     * @param before   The end date (exclusive) to filter the leaves.
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the filtered leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAllByStartBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateBefore(before, pageable), dtoMapper);
    }

    /**
     * Retrieves all leaves with an end date before the specified date.
     *
     * @param before   The end date (exclusive) to filter the leaves.
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the filtered leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAllByEndBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByEndDateBefore(before, pageable), dtoMapper);
    }

    /**
     * Retrieves all leaves between the specified start and end dates.
     *
     * @param after    The start date (inclusive) to filter the leaves.
     * @param before   The end date (exclusive) to filter the leaves.
     * @param pageable The pagination information.
     * @return A ResponseEntity object containing a Page of LeaveDTO objects representing the filtered leaves.
     */
    ResponseEntity<Page<LeaveDTO>> readAllBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByStartDateAfterAndEndDateBefore(after, before, pageable), dtoMapper);

    }

}
