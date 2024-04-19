package com.github.konradcz2001.medicalappointments.specialization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a Java interface named SpecializationRepository.
 * It extends the JpaRepository interface, which is a Spring Data JPA interface for generic CRUD operations on a repository for a specific type.
 * The repository is used to perform database operations on the Specialization entity.
 * <p>
 * The interface declares two methods:
 * - findFirstBySpecialization(String specialization): Returns an Optional object that may contain the first Specialization entity with the given specialization.
 * - existsBySpecialization(String specialization): Returns a boolean value indicating whether a Specialization entity with the given specialization exists in the database.
 * <p>
 * The interface is annotated with @Repository, indicating that it is a Spring Data repository component.
 * It is used to enable the automatic scanning and registration of the repository bean in the Spring application context.
 */
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    Optional<Specialization> findFirstBySpecialization(String specialization);
    boolean existsBySpecialization(String specialization);
}
