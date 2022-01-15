package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.Client;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByDeadline(LocalDateTime deadline);
    List<Visit> findAllByType(String type);
    List<Visit> findAllByDoctor(Doctor doctor);
    List<Visit> findAllByClient(Client client);
}
