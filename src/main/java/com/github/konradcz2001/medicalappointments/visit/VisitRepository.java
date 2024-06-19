package com.github.konradcz2001.medicalappointments.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * This code snippet represents a Spring Data JPA repository interface called VisitRepository.
 * <p>
 * It extends JpaRepository for Visit entities with Long as the identifier type.
 * The interface provides methods to retrieve Visit entities based on various criteria such as date, type of visit, doctor, client, price, and cancellation status.
 * The methods support pagination using Pageable.
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findAllByDateAfter(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateBefore(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateAfterAndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Visit> findAllByTypeOfVisitTypeContainingIgnoreCase(String type, Pageable pageable);
    Page<Visit> findAllByTypeOfVisit_Doctor_Id(Long doctorId, Pageable pageable);
    Page<Visit> findAllByTypeOfVisit_Doctor_IdAndIsCancelled(Long doctorId, boolean isCancelled, Pageable pageable);
    Page<Visit> findAllByClientId(Long client, Pageable pageable);
    Page<Visit> findAllByClientIdAndIsCancelled(Long clientId, boolean isCancelled, Pageable pageable);
    Page<Visit> findAllByTypeOfVisitPrice(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByTypeOfVisitPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByTypeOfVisitPriceGreaterThanEqual(BigDecimal price, Pageable pageable);

    List<Visit> findAllByTypeOfVisit_Doctor_Id(Long doctorId);
    List<Visit> findAllByClientId(Long client);
}
