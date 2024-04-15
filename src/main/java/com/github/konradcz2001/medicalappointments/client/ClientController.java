package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientResponseDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewResponseDTO;
import com.github.konradcz2001.medicalappointments.review.Review;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin
class ClientController {
    private final ClientService service;

    ClientController(final ClientService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<ClientResponseDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<Page<ClientResponseDTO>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<Page<ClientResponseDTO>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    @PostMapping
    ResponseEntity<?> createClient(@Valid @RequestBody Client client){
        return service.createClient(client);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody Client client){
        return service.updateCustomer(id, client);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        return service.deleteCustomer(id);
    }

    //TODO comments
    /**
     *
     *
     * @param clientId
     * @param review
     * @return
     */

    @PatchMapping("/{clientId}/add-review")
    ResponseEntity<?> addReview(@PathVariable Long clientId, @Valid @RequestBody Review review){
        return service.addReview(clientId, review);
    }


    @PatchMapping(value = "/{clientId}/remove-review")
    ResponseEntity<?> removeReview(@PathVariable Long clientId, @RequestParam Long reviewId){
        return service.removeReview(clientId, reviewId);
    }

    @PatchMapping(value = "/{clientId}/update-review")
    ResponseEntity<?> updateReview(@PathVariable Long clientId, @Valid @RequestBody Review review){
        return service.updateReview(clientId, review);
    }


    @GetMapping("/{clientId}/reviews")
    ResponseEntity<Page<ClientReviewResponseDTO>> readAllReviews(@PathVariable Long clientId, Pageable pageable){
        return service.readAllReviews(clientId, pageable);
    }
}
