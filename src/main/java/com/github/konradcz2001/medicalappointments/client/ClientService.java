package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTOMapper;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewDTO;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongReviewException;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTOMapper;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.CLIENT;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.DOCTOR;

@Service
class ClientService {
    private final ClientRepository repository;
    private final ReviewRepository reviewRepository;
    private final ClientDTOMapper dtoMapper;
    private final DoctorRepository doctorRepository;
    private final ReviewDTOMapper reviewDTOMapper;


    ClientService(ClientRepository repository, ReviewRepository reviewRepository, ClientDTOMapper dtoMapper, DoctorRepository doctorRepository, ReviewDTOMapper reviewDTOMapper) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
        this.dtoMapper = dtoMapper;
        this.doctorRepository = doctorRepository;
        this.reviewDTOMapper = reviewDTOMapper;
    }


    ResponseEntity<Page<ClientDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    ResponseEntity<ClientDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    ResponseEntity<Page<ClientDTO>> readAllByFirstName(String name, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContaining(name, pageable), dtoMapper);
    }

    ResponseEntity<Page<ClientDTO>> readAllByLastName(String surname, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContaining(surname, pageable), dtoMapper);
    }

    ResponseEntity<ClientDTO> createClient(Client client){
        client.setId(null);
        client.setReviews(new ArrayList<>());
        Client created = repository.save(client);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }

    ResponseEntity<?> updateClient(Long id, ClientDTO toUpdate){
        return repository.findById(id)
                .map(client -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, client));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    ResponseEntity<?> deleteClient(Long id){
        return repository.findById(id)
                .map(client -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    ResponseEntity<?> addReview(Long clientId, ReviewDTO toAdd){
        return repository.findById(clientId)
                .map(client -> {
                    Long doctorId = toAdd.doctorId();
                    Doctor doctor = doctorRepository.findById(doctorId)
                            .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

                    client.getReviews().forEach(review -> {
                        if(review.getClient().getId().equals(clientId) && review.getDoctor().getId().equals(doctorId)){
                            throw new WrongReviewException("Client with id = " + clientId + " already has a review for doctor with id = " + doctorId);
                        }
                    });

                    Review review = reviewDTOMapper.mapFromDTO(toAdd, new Review());
                    review.setClient(client);
                    review.setDoctor(doctor);
                    review.setDate(LocalDateTime.now());

                    //reviewRepository.save(review);
                    client.addReview(review);
                    repository.save(client);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));
    }


    ResponseEntity<?> updateReview(Long clientId, ReviewDTO toUpdate){
        return repository.findById(clientId)
                .map(client -> reviewRepository.findById(toUpdate.id())
                        .map(review -> {
                            reviewRepository.save(reviewDTOMapper.mapFromDTO(toUpdate, review));
                            return ResponseEntity.noContent().build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));
    }

//TODO cascade
    ResponseEntity<?> removeReview(Long clientId, Long reviewId){
        return repository.findById(clientId)
                .map(client -> reviewRepository.findById(reviewId)
                        .map(review -> {
                            client.removeReview(review);
                            repository.save(client);
                            return ResponseEntity.noContent().build();
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }



    ResponseEntity<Page<ClientReviewDTO>> readAllReviews(Long clientId, Pageable pageable){
        var clients = reviewRepository.findAllByClientId(clientId, pageable)
                .map(dtoMapper::applyForReview);
        if(clients.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(clients);
    }
}
