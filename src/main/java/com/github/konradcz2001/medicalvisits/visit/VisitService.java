package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.ClientFacade;
import com.github.konradcz2001.medicalvisits.doctor.DoctorFacade;
import org.springframework.stereotype.Service;

@Service
class VisitService {
    private final VisitRepository visitRepository;
    private final DoctorFacade doctorFacade;
    private final ClientFacade clientFacade;

    VisitService(final VisitRepository visitRepository, final DoctorFacade doctorFacade, final ClientFacade clientFacade) {
        this.visitRepository = visitRepository;
        this.doctorFacade = doctorFacade;
        this.clientFacade = clientFacade;
    }

    void addVisit(Visit visit) throws IllegalArgumentException {
        long doctorId = visit.getDoctor().getId();
        long clientId = visit.getClient().getId();

        doctorFacade.findById(doctorId)
                .ifPresentOrElse(
                        doctor -> {
                            clientFacade.findById(clientId)
                                    .ifPresentOrElse(
                                            client -> visitRepository.save(visit),
                                            () -> {
                                                throw new IllegalArgumentException("There is no client with such id");
                                            }
                                    );
                        }, () -> {
                            throw new IllegalArgumentException("There is no doctor with such id");
                        }
                );
    }


}