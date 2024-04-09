package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientFacade;
import com.github.konradcz2001.medicalappointments.doctor.DoctorFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;

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
        try {
            addVisit2(visit);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        Visit result = repository.save(visit);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    //TODO add visit
    void addVisit2(Visit visit) throws IllegalArgumentException {
        Long doctorId = visit.getDoctor().getId();
        Long clientId = visit.getClient().getId();

        doctorFacade.findById(doctorId)
                .ifPresentOrElse(
                        doctor -> {
                            visit.setDoctor(doctor);
                            //repository.save(visit);
                            clientFacade.findById(clientId)
                                    .ifPresentOrElse(
                                            visit::setClient,
                                            () -> {
                                                throw new IllegalArgumentException("There is no client with such id");
                                            }
                                    );
                        }, () -> {
                            throw new IllegalArgumentException("There is no doctor with such id");
                        }
                );
    }



    ResponseEntity<Page<Visit>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<Visit> readById(Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<?> deleteVisit(Long id){
        return repository.findById(id)
                .map(visit -> {
                    //TODO cascade type?
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());

    }


    public ResponseEntity<Page<Visit>> readAllByPrice(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPrice(price, pageable));
    }

    public ResponseEntity<Page<Visit>> readAllByPriceLessThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceLessThanEqual(price, pageable));
    }

    public ResponseEntity<Page<Visit>> readAllByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return returnResponse(() -> repository.findAllByPriceGreaterThanEqual(price, pageable));
    }
}