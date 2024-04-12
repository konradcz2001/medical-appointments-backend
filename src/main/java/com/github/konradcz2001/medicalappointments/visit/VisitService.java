package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientFacade;
import com.github.konradcz2001.medicalappointments.doctor.DoctorFacade;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.*;

@Service
class VisitService {
    private final VisitRepository repository;
    private final DoctorFacade doctorFacade;
    private final ClientFacade clientFacade;

    VisitService(final VisitRepository repository, final DoctorFacade doctorFacade, final ClientFacade clientFacade) {
        this.repository = repository;
        this.doctorFacade = doctorFacade;
        this.clientFacade = clientFacade;
    }

    ResponseEntity<?> createVisit(Visit visit){
        Long doctorId = visit.getDoctor().getId();
        Long clientId = visit.getClient().getId();

        doctorFacade.findById(doctorId)
                .ifPresentOrElse(
                        doctor -> {
                            visit.setDoctor(doctor);
                            clientFacade.findById(clientId)
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

    ResponseEntity<Page<Visit>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<Visit> readById(Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(VISIT, id));
    }

    ResponseEntity<Page<Visit>> readAllByTypeOfVisit(String type, Pageable pageable){
        return returnResponse(() -> repository.findAllByTypeContainingIgnoreCase(type, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByDateOfVisitAfter(LocalDateTime after, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateOfVisitAfter(after, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByDateOfVisitBefore(LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateOfVisitBefore(before, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByDateOfVisitBetween(LocalDateTime after, LocalDateTime before, Pageable pageable){
        return returnResponse(() -> repository.findAllByDateOfVisitAfterAndDateOfVisitBefore(after, before, pageable));
    }


    ResponseEntity<Page<Visit>> readAllByDoctorId(Long doctorId, Pageable pageable){
        return returnResponse(() -> repository.findAllByDoctorId(doctorId, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByClientId(Long clientId, Pageable pageable){
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


    ResponseEntity<Page<Visit>> readAllByPrice(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPrice(price, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceLessThanEqual(price, pageable));
    }

    ResponseEntity<Page<Visit>> readAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceGreaterThanEqual(price, pageable));
    }
}