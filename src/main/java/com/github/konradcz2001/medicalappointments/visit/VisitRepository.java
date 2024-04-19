package com.github.konradcz2001.medicalappointments.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This code snippet represents a Java interface called VisitRepository. It extends the JpaRepository interface from the Spring Data JPA framework, indicating that it is a repository interface for managing Visit entities.
 * <p>
 * The VisitRepository interface provides several methods for querying and retrieving Visit entities from the underlying data source. These methods include:
 * - findAllByDateAfter: Retrieves all Visit entities with a date after the specified LocalDateTime value.
 * - findAllByDateBefore: Retrieves all Visit entities with a date before the specified LocalDateTime value.
 * - findAllByDateAfterAndDateBefore: Retrieves all Visit entities with a date between the specified LocalDateTime values.
 * - findAllByTypeContainingIgnoreCase: Retrieves all Visit entities with a type containing the specified case-insensitive string.
 * - findAllByDoctorId: Retrieves all Visit entities with a doctor ID matching the specified Long value.
 * - findAllByClientId: Retrieves all Visit entities with a client ID matching the specified Long value.
 * - findAllByPrice: Retrieves all Visit entities with a price matching the specified BigDecimal value.
 * - findAllByPriceLessThanEqual: Retrieves all Visit entities with a price less than or equal to the specified BigDecimal value.
 * - findAllByPriceGreaterThanEqual: Retrieves all Visit entities with a price greater than or equal to the specified BigDecimal value.
 */
@Repository
interface VisitRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findAllByDateAfter(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateBefore(LocalDateTime date, Pageable pageable);
    Page<Visit> findAllByDateAfterAndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Visit> findAllByTypeContainingIgnoreCase(String type, Pageable pageable);
    Page<Visit> findAllByDoctorId(Long doctor, Pageable pageable);
    Page<Visit> findAllByClientId(Long client, Pageable pageable);
    Page<Visit> findAllByPrice(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Visit> findAllByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);
}
