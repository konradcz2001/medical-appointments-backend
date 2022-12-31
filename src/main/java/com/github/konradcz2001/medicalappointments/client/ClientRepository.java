package com.github.konradcz2001.medicalappointments.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByName(String name);
    List<Client> findAllBySurname(String surname);
}
