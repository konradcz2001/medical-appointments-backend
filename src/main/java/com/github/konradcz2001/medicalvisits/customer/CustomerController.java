package com.github.konradcz2001.medicalvisits.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
class CustomerController {
    private final CustomerRepository repository;

    CustomerController(final CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<Customer>> readAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> readById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    ResponseEntity<List<Customer>> readAllByName(@RequestParam String name){
        List<Customer> customers = repository.findAllBySurname(name);
        if(customers.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(customers);
    }

    @GetMapping(params = "surname")
    ResponseEntity<List<Customer>> readAllBySurname(@RequestParam String surname){
        List<Customer> customers = repository.findAllBySurname(surname);
        if(customers.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(customers);
    }

    @PostMapping
    ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        Customer result = repository.save(customer);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping
    ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
        if(repository.existsById(customer.getId())){
            repository.save(customer);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
