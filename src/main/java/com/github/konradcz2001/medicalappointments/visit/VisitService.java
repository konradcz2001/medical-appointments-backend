package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTOMapper;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

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

    ResponseEntity<Page<VisitDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    ResponseEntity<VisitDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    ResponseEntity<Page<VisitDTO>> readAllByTypeOfVisit(String type, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeContainingIgnoreCase(type, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfter(after, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateBefore(before, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateAfterAndDateBefore(after, before, pageable), dtoMapper);
    }


    ResponseEntity<Page<VisitDTO>> readAllByDoctorId(Long doctorId, Pageable pageable){
        return returnResponse(() -> repository.findAllByDoctorId(doctorId, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByClientId(Long clientId, Pageable pageable){
        return returnResponse(() -> repository.findAllByClientId(clientId, pageable), dtoMapper);
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


    ResponseEntity<Page<VisitDTO>> readAllByPrice(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPrice(price, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceLessThanEqual(price, pageable), dtoMapper);
    }

    ResponseEntity<Page<VisitDTO>> readAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceGreaterThanEqual(price, pageable), dtoMapper);
    }
}