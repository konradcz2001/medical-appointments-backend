package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByDateOfVisit(LocalDateTime date);
    List<Visit> findAllByType(String type);
    List<Visit> findAllByDoctor(Doctor doctor);
    List<Visit> findAllByClient(Client client);
}
