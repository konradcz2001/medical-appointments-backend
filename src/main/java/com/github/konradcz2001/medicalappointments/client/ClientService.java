package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTOMapper;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientResponseDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewResponseDTO;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.function.Supplier;

@Service
class ClientService {
    private final ClientRepository repository;
    private final ReviewFacade reviewFacade;

    ClientService(final ClientRepository repository, ReviewFacade reviewFacade) {
        this.repository = repository;
        this.reviewFacade = reviewFacade;
    }

    private ResponseEntity<Page<ClientResponseDTO>> returnResponse(Supplier<Page<Client>> suppliedClients) {
        var clients = suppliedClients.get()
                .map(ClientDTOMapper::apply);
        if(clients.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(clients);
    }


    ResponseEntity<Page<ClientResponseDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<ClientResponseDTO> readById(Long id){
        return repository.findById(id)
                .map(ClientDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<Page<ClientResponseDTO>> readAllByFirstName(String name, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContaining(name, pageable));
    }

    ResponseEntity<Page<ClientResponseDTO>> readAllByLastName(String surname, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContaining(surname, pageable));
    }

    ResponseEntity<?> createClient(Client client){
        client.setId(null);
        Client result = repository.save(client);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(ClientDTOMapper.apply(result));
    }

    ResponseEntity<?> updateCustomer(Long id, Client toUpdate){
        return repository.findById(id)
                .map(client -> {
                    toUpdate.setId(id);
                    return ResponseEntity.ok(ClientDTOMapper.apply(repository.save(toUpdate)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<?> deleteCustomer(Long id){
        return repository.findById(id)
                .map(client -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<?> addReview(Long clientId, Review toAdd){
        return repository.findById(clientId)
                .map(client -> {
                    client.getReviews().forEach(review -> {
                        if(review.getClient().getId().equals(clientId) &&
                                review.getDoctor().getId().equals(toAdd.getDoctor().getId())){
                            throw new IllegalArgumentException("This client already has a review for this doctor");
                        }
                    });
                    client.addReview(toAdd);
                    repository.save(client);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    ResponseEntity<?> updateReview(Long clientId, Review toUpdate){
        return repository.findById(clientId)
                .map(client -> reviewFacade.findById(toUpdate.getId())
                        .map(review -> {
                            reviewFacade.save(toUpdate);
                            return ResponseEntity.noContent().build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

//TODO cascade
    ResponseEntity<?> removeReview(Long clientId, Long reviewId){
        return repository.findById(clientId)
                .map(client -> reviewFacade.findById(reviewId)
                        .map(review -> {
                            client.removeReview(review);
                            repository.save(client);
                            return ResponseEntity.noContent().build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }



    ResponseEntity<Page<ClientReviewResponseDTO>> readAllReviews(Long clientId, Pageable pageable){
        var clients = reviewFacade.findAllByClientId(clientId, pageable)
                .map(ClientDTOMapper::applyForReview);
        if(clients.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(clients);
    }
}
