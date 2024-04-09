package com.github.konradcz2001.medicalappointments.doctor.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findAllByStartAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartAfterAndEndBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Leave> findAllByDoctorId(Long doctorId, Pageable pageable);
}
