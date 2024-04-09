package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin
class ClientController {
    private final ClientService facade;

    ClientController(final ClientService facade) {
        this.facade = facade;
    }

    @GetMapping
    ResponseEntity<Page<Client>> readAll(Pageable pageable){
        return facade.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Client> readById(@PathVariable Long id){
        return facade.readById(id);
    }

    @GetMapping(params = "firstName")
    ResponseEntity<Page<Client>> readAllByFirstName(@RequestParam String firstName, Pageable pageable){
        return facade.readAllByFirstName(firstName, pageable);
    }

    @GetMapping(params = "lastName")
    ResponseEntity<Page<Client>> readAllByLastName(@RequestParam String lastName, Pageable pageable){
        return facade.readAllByLastName(lastName, pageable);
    }

    @PostMapping
    ResponseEntity<?> createClient(@RequestBody Client client){
        return facade.createClient(client);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client client){
        return facade.updateCustomer(id, client);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        return facade.deleteCustomer(id);
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
    ResponseEntity<?> addReview(@PathVariable Long clientId, @RequestBody Review review){
        return facade.addReview(clientId, review);
    }


    @PatchMapping(value = "/{clientId}/remove-review")
    ResponseEntity<?> removeReview(@PathVariable Long clientId, @RequestParam Long reviewId){
        return facade.removeReview(clientId, reviewId);
    }

    @PatchMapping(value = "/{clientId}/update-review")
    ResponseEntity<?> updateReview(@PathVariable Long clientId, @RequestBody Review review){
        return facade.updateReview(clientId, review);
    }


    @GetMapping("/{clientId}/reviews")
    ResponseEntity<Page<Review>> readAllReviews(@PathVariable Long clientId, Pageable pageable){
        return facade.readAllReviews(clientId, pageable);
    }
}
