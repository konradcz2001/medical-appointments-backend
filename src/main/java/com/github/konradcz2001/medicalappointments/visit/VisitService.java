package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.ClientFacade;
import com.github.konradcz2001.medicalappointments.doctor.DoctorFacade;
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
                            visit.setDoctor(doctor);
                            clientFacade.findById(clientId)
                                    .ifPresentOrElse(
                                            client -> {
                                                visit.setClient(client);
                                                visitRepository.save(visit);
                                            },
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