package com.github.konradcz2001.medicalvisits.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin
class ClientController {
    private final ClientRepository repository;

    ClientController(final ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<Client>> readAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Client> readById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    ResponseEntity<List<Client>> readAllByName(@RequestParam String name){
        List<Client> clients = repository.findAllBySurname(name);
        if(clients.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(clients);
    }

    @GetMapping(params = "surname")
    ResponseEntity<List<Client>> readAllBySurname(@RequestParam String surname){
        List<Client> clients = repository.findAllBySurname(surname);
        if(clients.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(clients);
    }

    @PostMapping
    ResponseEntity<?> addCustomer(@RequestBody Client clients){
        Client result = repository.save(clients);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping
    ResponseEntity<?> updateCustomer(@RequestBody Client clients){
        if(repository.existsById(clients.getId())){
            repository.save(clients);
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
