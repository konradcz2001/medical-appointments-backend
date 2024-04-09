package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

import static com.github.konradcz2001.medicalappointments.MedicalAppointmentsApplication.returnResponse;

@Service
class ClientService {
    private final ClientRepository repository;
    private final ReviewFacade reviewFacade;

    ClientService(final ClientRepository repository, ReviewFacade reviewFacade) {
        this.repository = repository;
        this.reviewFacade = reviewFacade;
    }


    ResponseEntity<Page<Client>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }

    ResponseEntity<Client> readById(Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<Page<Client>> readAllByFirstName(String name, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContaining(name, pageable));
    }

    ResponseEntity<Page<Client>> readAllByLastName(String surname, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContaining(surname, pageable));
    }

    ResponseEntity<?> createClient(Client client){
        client.setId(null);
        Client result = repository.save(client);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    ResponseEntity<?> updateCustomer(Long id, Client toUpdate){
        return repository.findById(id)
                .map(client -> {
                    toUpdate.setId(id);
                    return ResponseEntity.ok(repository.save(toUpdate));
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



    ResponseEntity<Page<Review>> readAllReviews(Long clientId, Pageable pageable){
        return returnResponse(() -> reviewFacade.findAllByClientId(clientId, pageable));
    }
}
