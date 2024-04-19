package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewDTO;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
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
    ResponseEntity<Page<ClientDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<Page<ClientDTO>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return service.readAllByFirstName(firstName, pageable);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<Page<ClientDTO>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return service.readAllByLastName(lastName, pageable);
    }

    @PostMapping
    ResponseEntity<ClientDTO> createClient(@Valid @RequestBody Client client){
        return service.createClient(client);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody ClientDTO client){
        return service.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        return service.deleteClient(id);
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
    ResponseEntity<?> addReview(@PathVariable Long clientId, @Valid @RequestBody ReviewDTO review){
        return service.addReview(clientId, review);
    }


    @PatchMapping(value = "/{clientId}/remove-review")
    ResponseEntity<?> removeReview(@PathVariable Long clientId, @RequestParam Long reviewId){
        return service.removeReview(clientId, reviewId);
    }

    @PatchMapping(value = "/{clientId}/update-review")
    ResponseEntity<?> updateReview(@PathVariable Long clientId, @Valid @RequestBody ReviewDTO review){
        return service.updateReview(clientId, review);
    }


    @GetMapping("/{clientId}/reviews")
    ResponseEntity<Page<ClientReviewDTO>> readAllReviews(@PathVariable Long clientId, Pageable pageable){
        return service.readAllReviews(clientId, pageable);
    }
}
