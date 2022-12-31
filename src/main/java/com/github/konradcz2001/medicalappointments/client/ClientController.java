package com.github.konradcz2001.medicalappointments.client;

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
        List<Client> clients = repository.findAllByName(name);
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
    ResponseEntity<?> addCustomer(@RequestBody Client client){
        Client result = repository.save(client);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody Client client){
        if(repository.existsById(id)){
            client.setId(id);
            repository.save(client);
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
