package com.github.konradcz2001.medicalappointments.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    Page<Client> findAllByFirstNameContaining(String firstName, Pageable pageable);
    Page<Client> findAllByLastNameContaining(String lastName, Pageable pageable);
}
