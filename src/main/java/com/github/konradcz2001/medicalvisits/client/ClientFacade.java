package com.github.konradcz2001.medicalvisits.client;

import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientFacade {
    private final ClientRepository repository;

    ClientFacade(final ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> findById(final long id) {
        return repository.findById(id);
    }
}
