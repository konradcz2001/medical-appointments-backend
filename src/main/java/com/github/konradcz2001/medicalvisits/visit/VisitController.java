package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.Client;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/visits")
@CrossOrigin
class VisitController {
    private final VisitRepository repository;
    private final VisitService service;

    VisitController(final VisitRepository repository, final VisitService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<Visit>> readAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Visit> readById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "type")
    ResponseEntity<List<Visit>> readAllByTypeOfVisit(@RequestParam String type){
        List<Visit> visits = repository.findAllByType(type);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @GetMapping(params = "date")
    ResponseEntity<List<Visit>> readAllByDateOfVisit(@RequestParam LocalDateTime date){
        List<Visit> visits = repository.findAllByDateOfVisit(date);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @GetMapping(params = "doctor")
    ResponseEntity<List<Visit>> readAllByDoctor(@RequestParam Doctor doctor){
        List<Visit> visits = repository.findAllByDoctor(doctor);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @GetMapping(params = "client")
    ResponseEntity<List<Visit>> readAllByClient(@RequestParam Client client){
        List<Visit> visits = repository.findAllByClient(client);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @PostMapping
    ResponseEntity<?> addVisit(@RequestBody Visit visit){
        try {
            service.addVisit(visit);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        Visit result = repository.save(visit);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateVisit(@PathVariable long id, @RequestBody Visit visit){
        if(repository.existsById(id)){
            visit.setId(id);
            repository.save(visit);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVisit(@PathVariable long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
