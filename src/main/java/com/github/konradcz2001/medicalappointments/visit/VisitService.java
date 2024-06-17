package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.exceptions.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTOMapper;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

/**
 * This is a service class that handles operations related to visits.
 * It provides methods for creating, reading, updating, and deleting visits.
 * The class also includes methods for retrieving visits based on different criteria such as ID, type, date, price, doctor, and client.
 * The class uses a VisitRepository, DoctorRepository, ClientRepository, and VisitDTOMapper for data access and mapping.
 */
@Service
class VisitService {
    private final VisitRepository repository;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;
    private final VisitDTOMapper dtoMapper;

    VisitService(final VisitRepository repository, final DoctorRepository doctorRepository, final ClientRepository clientRepository, VisitDTOMapper dtoMapper) {
        this.repository = repository;
        this.doctorRepository = doctorRepository;
        this.clientRepository = clientRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Creates a new visit based on the provided VisitDTO.
     *
     * @param visitDTO The VisitDTO object containing the details of the visit.
     * @return A ResponseEntity containing the created VisitDTO object.
     * @throws ResourceNotFoundException if the doctor or client with the specified IDs are not found.
     */
    @Transactional
    ResponseEntity<VisitDTO> createVisit(VisitDTO visitDTO){
        Long doctorId = visitDTO.typeOfVisit().doctorId();
        Long clientId = visitDTO.clientId();
        //TODO check if doctor is available

        return doctorRepository.findById(doctorId)
                .map(doctor -> clientRepository.findById(clientId)
                            .map( client -> {
                                    Visit visit = new Visit();
                                    visit.setClient(client);
                                    TypeOfVisit typeOfVisit = new TypeOfVisit();
                                    typeOfVisit.setDoctor(doctor);
                                    visit.setTypeOfVisit(typeOfVisit);
                                    Visit result = repository.save(dtoMapper.mapFromDTO(visitDTO, visit));
                                    return ResponseEntity.created(URI.create("/" + result.getId())).body(dtoMapper.mapToDTO(result));
                            })
                            .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    /**
     * Retrieves all visits with pagination.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    /**
     * Retrieves a visit with the specified ID.
     *
     * @param id the ID of the visit to retrieve
     * @return a ResponseEntity containing the VisitDTO object representing the visit
     * @throws ResourceNotFoundException if the visit with the specified ID is not found
     */
    ResponseEntity<VisitDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    /**
     * Retrieves all visits with a type containing the specified case-insensitive string.
     *
     * @param type     the string to search for in the visit type
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByTypeOfVisit(String type, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeOfVisitTypeContainingIgnoreCase(type, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a date of visit after the specified date.
     *
     * @param after    the LocalDateTime representing the starting date
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a date of visit before the specified date.
     *
     * @param before   the LocalDateTime representing the ending date
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a date of visit between the specified after and before dates.
     *
     * @param after    the LocalDateTime representing the starting date
     * @param before   the LocalDateTime representing the ending date
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable), dtoMapper);
    }


    /**
     * Retrieves all visits with a doctor of visit ID matching the specified ID.
     *
     * @param doctorId       the ID of the doctor of visit to search for
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByDoctorId(Long doctorId, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeOfVisit_Doctor_Id(doctorId, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a doctor ID matching the specified ID and cancellation status matching the provided boolean value.
     *
     * @param doctorId the ID of the doctor to search for in the visits
     * @param isCancelled the cancellation status to filter the visits
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByDoctorIdAndCancellationStatus(Long doctorId, boolean isCancelled, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeOfVisit_Doctor_IdAndIsCancelled(doctorId, isCancelled, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits associated with the specified client ID.
     *
     * @param clientId the ID of the client
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByClientId(Long clientId, Pageable pageable){
        return returnResponse(() -> repository.findAllByClientId(clientId, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits associated with the specified client ID and cancellation status.
     *
     * @param clientId the ID of the client
     * @param isCancelled the cancellation status to filter the visits
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByClientIdAndCancellationStatus(Long clientId, boolean isCancelled, Pageable pageable){
        return returnResponse(() -> repository.findAllByClientIdAndIsCancelled(clientId, isCancelled, pageable), dtoMapper);
    }

    /**
     * Updates a visit with the specified ID.
     *
     * @param id       the ID of the visit to update
     * @param toUpdate the VisitDTO object containing the updated visit information
     * @return a ResponseEntity with no content if the visit is successfully updated
     * @throws ResourceNotFoundException if the visit with the specified ID is not found
     */
    @Transactional
    ResponseEntity<?> updateVisit(Long id, VisitDTO toUpdate){
        return repository.findById(id)
                .map(visit -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, visit));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    /**
     * Deletes a visit with the specified ID.
     *
     * @param id the ID of the visit to delete
     * @return a ResponseEntity with no content if the visit is successfully deleted
     * @throws ResourceNotFoundException if the visit with the specified ID is not found
     */
    @Transactional
    ResponseEntity<?> deleteVisit(Long id){
        return repository.findById(id)
                .map(visit -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));

    }

    /**
     * Cancels a visit with the specified ID by setting the 'cancelled' flag to true.
     *
     * @param id the ID of the visit to cancel
     * @return a ResponseEntity with no content if the visit is successfully canceled
     * @throws ResourceNotFoundException if the visit with the specified ID is not found
     */
    @Transactional
    ResponseEntity<?> cancelVisit(Long id){
        return repository.findById(id)
                .map(visit -> {
                    visit.setCancelled(true);
                    repository.save(visit);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));

    }


    /**
     * Retrieves all visits with a price matching the specified price.
     *
     * @param price    the price of the visits to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByPrice(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByTypeOfVisitPrice(price, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a price less than or equal to the specified price.
     *
     * @param price    the maximum price of the visits to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByTypeOfVisitPriceLessThanEqual(price, pageable), dtoMapper);
    }

    /**
     * Retrieves all visits with a price greater than the specified price.
     *
     * @param price    the minimum price of the visits to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of VisitDTO objects representing the visits
     */
    ResponseEntity<Page<VisitDTO>> readAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByTypeOfVisitPriceGreaterThanEqual(price, pageable), dtoMapper);
    }
}