package com.github.konradcz2001.medicalappointments.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAllByFirstNameContaining(String firstName, Pageable pageable);
    Page<Client> findAllByLastNameContaining(String lastName, Pageable pageable);
}
