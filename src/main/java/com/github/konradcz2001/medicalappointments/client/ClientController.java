package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewDTO;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This is a Java class representing a REST controller for managing clients.
 * It handles various CRUD operations for clients and their reviews.
 * The class is annotated with @RestController and @RequestMapping to define the base URL for client-related endpoints.
 * It also has @CrossOrigin annotation to allow cross-origin requests.
 */

@RestController
@RequestMapping("/clients")
class ClientController {
    private final ClientService service;

    ClientController(final ClientService service) {
        this.service = service;
    }

    /**
     * Retrieves all clients with pagination.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ClientDTO objects
     */
    @Operation(summary = "Retrieves all clients with pagination and parameter.",
            description = "All these parameters are not required. Swagger does not distinguish between individual endpoints. For more details, see the code."
    )
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<ClientDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id the ID of the client
     * @return a ResponseEntity containing the ClientDTO object
     */
    @Operation(summary = "Retrieves a client by their ID.")
    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    /**
     * Retrieves all clients with the given first name.
     *
     * @param firstName the first name of the clients to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ClientDTO objects
     */
    //@Operation(summary = "Retrieves all clients with the given first name.")
    @GetMapping(params = "firstName")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<ClientDTO>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    /**
     * Retrieves all clients with the given last name.
     *
     * @param lastName the last name of the clients to retrieve
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ClientDTO objects
     */
    //@Operation(summary = "Retrieves all clients with the given last name.")
    @GetMapping(params = "lastName")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Page<ClientDTO>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    /**
     * Creates a new client.
     *
     * @param client the client object to be created
     * @return a ResponseEntity containing the created ClientDTO object
     */
    @Operation(summary = "Creates a new client.")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ClientDTO> createClient(@Valid @RequestBody Client client){
        return service.createClient(client);
    }

    /**
     * Updates a client with the given ID.
     *
     * @param id the ID of the client to update
     * @param client the updated ClientDTO object
     * @return a ResponseEntity indicating the success or failure of the update operation
     */
    @Operation(summary = "Updates a client with the given ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO client){
        return service.updateClient(id, client);
    }

    /**
     * Deletes a client with the given ID.
     *
     * @param id the ID of the client to delete
     * @return a ResponseEntity indicating the success or failure of the delete operation
     */
    @Operation(summary = "Deletes a client with the given ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        return service.deleteClient(id);
    }

    /**
     * Adds a review for a client.
     *
     * @param clientId the ID of the client
     * @param review the ReviewDTO object representing the review to be added
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @Operation(summary = "Adds a review for a client.")
    @PatchMapping("/{clientId}/add-review")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    ResponseEntity<?> addReview(@PathVariable Long clientId, @Valid @RequestBody ReviewDTO review){
        return service.addReview(clientId, review);
    }

    /**
     * Removes a review for a client.
     *
     * @param clientId the ID of the client
     * @param reviewId the ID of the review to be removed
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @Operation(summary = "Removes a review for a client.")
    @PatchMapping(value = "/{clientId}/remove-review")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    ResponseEntity<?> removeReview(@PathVariable Long clientId, @RequestParam Long reviewId){
        return service.removeReview(clientId, reviewId);
    }

    /**
     * Updates a client review.
     *
     * @param clientId the ID of the client
     * @param review the ReviewDTO object representing the updated review
     * @return a ResponseEntity indicating the success or failure of the update operation
     */
    @Operation(summary = "Updates a client review.")
    @PatchMapping(value = "/{clientId}/update-review")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    ResponseEntity<?> updateReview(@PathVariable Long clientId, @Valid @RequestBody ReviewDTO review){
        return service.updateReview(clientId, review);
    }

    /**
     * Retrieves all reviews for a client with the given ID.
     *
     * @param clientId the ID of the client
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of ClientReviewDTO objects
     */
    @Operation(summary = "Retrieves all reviews for a client with the given ID.")
    @GetMapping("/{clientId}/reviews")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    ResponseEntity<Page<ClientReviewDTO>> readAllReviews(@PathVariable Long clientId, Pageable pageable){
        return service.readAllReviews(clientId, pageable);
    }
}
