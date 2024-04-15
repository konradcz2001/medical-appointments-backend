package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTOMapper;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

@Service
class VisitService {
    private final VisitRepository repository;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;

    VisitService(final VisitRepository repository, final DoctorRepository doctorRepository, final ClientRepository clientRepository) {
        this.repository = repository;
        this.doctorRepository = doctorRepository;
        this.clientRepository = clientRepository;
    }

    private ResponseEntity<Page<VisitResponseDTO>> returnResponse(Supplier<Page<Visit>> suppliedVisits) {
        var visits = suppliedVisits.get()
                .map(VisitDTOMapper::apply);
        if(visits.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(visits);
    }

    ResponseEntity<?> createVisit(Visit visit){
        Long doctorId = visit.getDoctor().getId();
        Long clientId = visit.getClient().getId();

        doctorRepository.findById(doctorId)
                .ifPresentOrElse(
                        doctor -> {
                            visit.setDoctor(doctor);
                            clientRepository.findById(clientId)
                                    .ifPresentOrElse(
                                            visit::setClient,
                                            () -> {
                                                throw new ResourceNotFoundException(CLIENT, clientId);
                                            }
                                    );
                        }, () -> {
                            throw new ResourceNotFoundException(DOCTOR, doctorId);
                        }
                );
        Visit result = repository.save(visit);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    ResponseEntity<Page<VisitResponseDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<VisitResponseDTO> readById(Long id){
        return repository.findById(id)
                .map(VisitDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByTypeOfVisit(String type, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeContainingIgnoreCase(type, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable));
    }


    ResponseEntity<Page<VisitResponseDTO>> readAllByDoctorId(Long doctorId, Pageable pageable){
        return returnResponse(() -> repository.findAllByDoctorId(doctorId, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByClientId(Long clientId, Pageable pageable){
        return returnResponse(() -> repository.findAllByClientId(clientId, pageable));
    }


    ResponseEntity<?> updateVisit(Long id, Visit toUpdate){
        return repository.findById(id)
                .map(visit -> {
                    toUpdate.setId(id);
                    //TODO Doctor and Client?
                    return ResponseEntity.ok(repository.save(toUpdate));
                })
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    ResponseEntity<?> deleteVisit(Long id){
        return repository.findById(id)
                .map(visit -> {
                    //TODO cascade type?
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));

    }


    ResponseEntity<Page<VisitResponseDTO>> readAllByPrice(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPrice(price, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceLessThanEqual(price, pageable));
    }

    ResponseEntity<Page<VisitResponseDTO>> readAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceGreaterThanEqual(price, pageable));
    }
}