package com.github.konradcz2001.medicalappointments.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * This code snippet represents the LeaveRepository interface.
 * <p>
 * The LeaveRepository interface is a Spring Data JPA repository interface that provides methods for querying the "leaves" table in the database.
 * It extends the JpaRepository interface and is annotated with the @Repository annotation.
 * <p>
 * The interface defines the following methods:
 * - findAllByStartDateAfter: Retrieves all leaves with a start date after the specified date.
 * - findAllByEndDateAfter: Retrieves all leaves with an end date after the specified date.
 * - findAllByStartDateBefore: Retrieves all leaves with a start date before the specified date.
 * - findAllByEndDateBefore: Retrieves all leaves with an end date before the specified date.
 * - findAllByStartDateAfterAndEndDateBefore: Retrieves all leaves with a start date after the specified "after" date and an end date before the specified "before" date.
 * - findAllByDoctorId: Retrieves all leaves for a specific doctor ID.
 * <p>
 * Note: The interface uses Spring Data JPA's Pageable interface for pagination.
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findAllByStartDateAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndDateAfter(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartDateBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByEndDateBefore(LocalDateTime date, Pageable pageable);
    Page<Leave> findAllByStartDateAfterAndEndDateBefore(LocalDateTime after, LocalDateTime before, Pageable pageable);
    Page<Leave> findAllByDoctorId(Long doctorId, Pageable pageable);
}
