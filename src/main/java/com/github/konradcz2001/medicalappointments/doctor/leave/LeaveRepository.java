package com.github.konradcz2001.medicalappointments.doctor.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findAllBySinceWhenAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByTillWhenAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllBySinceWhenBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByTillWhenBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllBySinceWhenAfterAndTillWhenBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
}
