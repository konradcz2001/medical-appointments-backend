package com.github.konradcz2001.medicalappointments.doctor.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findAllByStartDateAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndDateAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartDateBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndDateBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartDateAfterAndEndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Leave> findAllByDoctorId(Long doctorId, Pageable pageable);
}
