package com.github.konradcz2001.medicalappointments.client;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientFacade {
    private final ClientRepository repository;

    public ClientFacade(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> findById(final Long id) {
        return repository.findById(id);
    }
}
