package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTOMapper;
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

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.CLIENT;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.DOCTOR;


/**
 * Service class for managing clients and their reviews.
 * <p>
 * This class provides methods for retrieving, creating, updating, and deleting clients, as well as adding, updating, and removing reviews for clients.
 * It also includes methods for retrieving all clients, retrieving clients by ID, retrieving clients by first name or last name, and retrieving all reviews for a specific client.
 * The class uses repositories and mappers to interact with the database and map entities to DTOs.
 * <p>
 * The methods in this class handle various exceptions, such as EmptyPageException, ResourceNotFoundException, and WrongReviewException, and return appropriate responses using ResponseEntity.
 * The methods are annotated with appropriate JavaDoc comments to describe their functionality, parameters, and return values.
 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the Spring framework.
 */
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


    /**
     * Retrieves all clients.
     * <p>
     * This method retrieves all clients from the repository and returns them as a pageable list of ClientDTO objects wrapped
     * in a ResponseEntity. The clients are retrieved using the findAll method of the repository, passing the provided pageable
     * object as a parameter. The resulting page of clients is then mapped to a page of ClientDTO objects using the dtoMapper.
     * If the resulting page is empty, an EmptyPageException is thrown. Otherwise, the page of ClientDTO objects is wrapped in
     * a ResponseEntity with a status of 200 OK and returned.
     *
     * @param pageable the pageable object used for pagination
     * @return a ResponseEntity containing a pageable list of ClientDTO objects
     * @throws EmptyPageException if the resulting page is empty
     */
    ResponseEntity<Page<ClientDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    /**
     * Retrieves a client by ID.
     * <p>
     * This method retrieves a client from the repository based on the provided ID. The client is retrieved using the findById method
     * of the repository, passing the ID as a parameter. If the client is found, it is mapped to a ClientDTO object using the dtoMapper.
     * The resulting ClientDTO object is then wrapped in a ResponseEntity with a status of 200 OK and returned. If the client is not
     * found, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the client
     * @return a ResponseEntity containing the ClientDTO object
     * @throws ResourceNotFoundException if the client is not found in the repository
     */
    ResponseEntity<ClientDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    /**
     * Retrieves all clients with a matching first name.
     * <p>
     * This method retrieves all clients from the repository whose first name contains the provided name parameter. The clients are
     * retrieved using the findAllByFirstNameContaining method of the repository, passing the name and pageable object as parameters.
     * The resulting page of clients is then mapped to a page of ClientDTO objects using the dtoMapper. If the resulting page is empty,
     * an EmptyPageException is thrown. Otherwise, the page of ClientDTO objects is wrapped in a ResponseEntity with a status of 200 OK
     * and returned.
     *
     * @param name the first name to search for
     * @param pageable the pageable object used for pagination
     * @return a ResponseEntity containing a pageable list of ClientDTO objects
     * @throws EmptyPageException if the resulting page is empty
     */
    ResponseEntity<Page<ClientDTO>> readAllByFirstName(String name, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContaining(name, pageable), dtoMapper);
    }

    /**
     * Retrieves all clients with a matching last name.
     * <p>
     * This method retrieves all clients from the repository whose last name contains the provided surname parameter. The clients are
     * retrieved using the findAllByLastNameContaining method of the repository, passing the surname and pageable object as parameters.
     * The resulting page of clients is then mapped to a page of ClientDTO objects using the dtoMapper. If the resulting page is empty,
     * an EmptyPageException is thrown. Otherwise, the page of ClientDTO objects is wrapped in a ResponseEntity with a status of 200 OK
     * and returned.
     *
     * @param surname the last name to search for
     * @param pageable the pageable object used for pagination
     * @return a ResponseEntity containing a pageable list of ClientDTO objects
     * @throws EmptyPageException if the resulting page is empty
     */
    ResponseEntity<Page<ClientDTO>> readAllByLastName(String surname, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContaining(surname, pageable), dtoMapper);
    }

    /**
     * Creates a new client.
     * <p>
     * This method creates a new client in the repository based on the provided Client object. The ID of the client is set to null,
     * and an empty list of reviews is assigned to the client. The client is then saved in the repository using the save method.
     * The resulting created client is mapped to a ClientDTO object using the dtoMapper. Finally, a ResponseEntity is returned with
     * a status of 201 Created and the URI of the created client in the Location header.
     *
     * @param client the Client object representing the client to be created
     * @return a ResponseEntity containing the created ClientDTO object
     */
    ResponseEntity<ClientDTO> createClient(Client client){
        client.setId(null);
        client.setReviews(new ArrayList<>());
        Client created = repository.save(client);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }

    /**
     * Updates a client.
     * <p>
     * This method updates a client in the repository based on the provided ID and ClientDTO object. The method first retrieves the client
     * from the repository using the findById method, passing the ID as a parameter. If the client is found, the ClientDTO object is mapped
     * to the client using the mapFromDTO method of the dtoMapper. The updated client is then saved in the repository using the save method.
     * Finally, a ResponseEntity with a status of 204 No Content is returned to indicate a successful operation.
     *
     * @param id the ID of the client to update
     * @param toUpdate the ClientDTO object representing the updated client details
     * @return a ResponseEntity with a status of 204 No Content
     * @throws ResourceNotFoundException if the client is not found in the repository
     */
    ResponseEntity<?> updateClient(Long id, ClientDTO toUpdate){
        return repository.findById(id)
                .map(client -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, client));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    /**
     * Deletes a client.
     * <p>
     * This method deletes a client from the repository based on the provided ID. The method first retrieves the client from the repository
     * using the findById method, passing the ID as a parameter. If the client is found, it is deleted from the repository using the deleteById
     * method. Finally, a ResponseEntity with a status of 204 No Content is returned to indicate a successful operation.
     *
     * @param id the ID of the client to delete
     * @return a ResponseEntity with a status of 204 No Content
     * @throws ResourceNotFoundException if the client is not found in the repository
     */
    ResponseEntity<?> deleteClient(Long id){
        return repository.findById(id)
                .map(client -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, id));
    }

    /**
     * Adds a review for a client.
     * <p>
     * This method adds a review for a client identified by the given client ID. The review is created based on the provided
     * ReviewDTO object. The method first checks if the client and doctor exist in the repository. If the client already has
     * a review for the same doctor, a WrongReviewException is thrown. Otherwise, a new Review object is created and mapped
     * from the ReviewDTO. The client, doctor, and current date are set for the review. The review is then added to the client's
     * list of reviews and saved in the repository. Finally, a ResponseEntity with a status of 204 No Content is returned to
     * indicate a successful operation.
     *
     * @param clientId the ID of the client
     * @param toAdd the ReviewDTO object containing the review details
     * @return a ResponseEntity with a status of 204 No Content
     * @throws ResourceNotFoundException if the client or doctor is not found in the repository
     * @throws WrongReviewException if the client already has a review for the same doctor
     */
    ResponseEntity<?> addReview(Long clientId, ReviewDTO toAdd){
        return repository.findById(clientId)
                .map(client -> {
                    Long doctorId = toAdd.doctorId();
                    Doctor doctor = doctorRepository.findById(doctorId)
                            .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));

                    client.getReviews().forEach(review -> {
                        if(review.getClient().getId().equals(clientId) && review.getDoctor().getId().equals(doctorId)){
                            throw new WrongReviewException("Client with id = " + clientId + " already has a review for doctor with id = " + doctorId + ", id of that review = " + review.getId());
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

    /**
     * Updates a review for a client.
     * <p>
     * This method updates the review for a client with the specified client ID. It first checks if the client exists in the repository.
     * If the client exists, it searches for the review with the specified review ID in the client's list of reviews.
     * If the review is found, it updates the review's details with the information provided in the 'toUpdate' parameter.
     * The updated review is then saved in the review repository.
     * If the review is not found, a WrongReviewException is thrown.
     * If the client is not found, a ResourceNotFoundException is thrown.
     *
     * @param clientId The ID of the client.
     * @param toUpdate The updated review details.
     * @return A ResponseEntity with a no content status if the review is successfully updated.
     * @throws WrongReviewException If the client does not have a review with the specified review ID.
     * @throws ResourceNotFoundException If the client with the specified client ID is not found.
     */
    ResponseEntity<?> updateReview(Long clientId, ReviewDTO toUpdate){
        return repository.findById(clientId)
                .map(client -> client.getReviews().stream()
                                .filter(review -> review.getId().equals(toUpdate.id())).findAny()
                                .map(review -> {
                                            reviewRepository.save(reviewDTOMapper.mapFromDTO(toUpdate, review));
                                            return ResponseEntity.noContent().build();
                                }).orElseThrow(() -> new WrongReviewException("Client with id = " + clientId + " does not have a review with id = " + toUpdate.id())))
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));
    }

    /**
     * Removes a review from a client.
     * <p>
     * This method removes a review from a client by finding the client with the given client ID and then
     * searching for the review with the given review ID in the client's list of reviews. If the review is found,
     * it is removed from the list and the client is saved back to the repository. If the review is not found,
     * a WrongReviewException is thrown with an appropriate error message. If the client is not found,
     * a ResourceNotFoundException is thrown with an appropriate error message.
     *
     * @param clientId the ID of the client
     * @param reviewId the ID of the review to be removed
     * @return a ResponseEntity with a no content status if the review is successfully removed
     * @throws WrongReviewException if the client does not have a review with the given review ID
     * @throws ResourceNotFoundException if the client with the given client ID is not found
     */
    ResponseEntity<?> removeReview(Long clientId, Long reviewId){
        return repository.findById(clientId)
                .map(client -> client.getReviews().stream()
                        .filter(review -> review.getId().equals(reviewId)).findAny()
                        .map(review -> {
                            client.removeReview(review);
                            repository.save(client);
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongReviewException("Client with id = " + clientId + " does not have a review with id = " + reviewId)))
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));
    }


    /**
     * Retrieves all reviews for a specific client.
     *
     * @param clientId the ID of the client
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ClientReviewDTO objects
     * @throws EmptyPageException if the page is empty
     */
    ResponseEntity<Page<ClientReviewDTO>> readAllReviews(Long clientId, Pageable pageable){
        var clients = reviewRepository.findAllByClientId(clientId, pageable)
                .map(dtoMapper::mapToClientReviewDTO);
        if(clients.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(clients);
    }
}
