package com.github.konradcz2001.medicalappointments.visit.type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface represents a repository for TypeOfVisit entities, extending JpaRepository.
 * It provides a method to find all TypeOfVisit entities by a specific doctor's ID with pagination support.
 */
@Repository
public interface TypeOfVisitRepository extends JpaRepository<TypeOfVisit, Long> {
    Page<TypeOfVisit> findAllByDoctorId(Long id, Pageable pageable);
    Page<TypeOfVisit> findAllByDoctorIdAndIsActive(Long id, boolean isActive, Pageable pageable);
}
