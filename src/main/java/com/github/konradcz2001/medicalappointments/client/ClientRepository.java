package com.github.konradcz2001.medicalappointments.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This code snippet represents a Java interface called ClientRepository.
 * It extends the JpaRepository interface, which is a Spring Data JPA interface for generic CRUD operations on a repository for a specific type.
 * The ClientRepository interface is annotated with @Repository, indicating that it is a Spring repository component.
 * <p>
 * The interface declares several methods:
 * - findByEmail(String email): This method is used to find a client by their email address. It returns an Optional<Client> object, which may or may not contain a client.
 * - existsByEmail(String email): This method is used to check if client exist by email address.
 * - findAllByFirstNameContaining(String firstName, Pageable pageable): This method is used to find all clients whose first name contains a specific string. It returns a Page<Client> object, which represents a paginated list of clients.
 * - findAllByLastNameContaining(String lastName, Pageable pageable): This method is used to find all clients whose last name contains a specific string. It also returns a Page<Client> object.
 * <p>
 * The ClientRepository interface is generic, with the type parameter being Client. The Long type parameter represents the type of the client's ID.
 * <p>
 * This interface is used for accessing and manipulating client data in the database.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Boolean existsByEmail(String email);

    Page<Client> findAllByFirstNameContaining(String firstName, Pageable pageable);
    Page<Client> findAllByLastNameContaining(String lastName, Pageable pageable);
}
