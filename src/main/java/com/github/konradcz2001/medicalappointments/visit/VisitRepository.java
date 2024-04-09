package com.github.konradcz2001.medicalappointments.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

interface VisitRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findAllByDateOfVisitAfter(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateOfVisitBefore(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateOfVisitAfterAndDateOfVisitBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Visit> findAllByTypeContainingIgnoreCase(String type, Pageable pageable);
    Page<Visit> findAllByDoctorId(Long doctor, Pageable pageable);
    Page<Visit> findAllByClientId(Long client, Pageable pageable);
    Page<Visit> findAllByPrice(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);
}
