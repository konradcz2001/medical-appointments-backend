package com.github.konradcz2001.medicalvisits.visit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/visits")
class VisitController {
    VisitRepository repository;

    VisitController(final VisitRepository customerRepository) {
        this.repository = customerRepository;
    }

    @GetMapping
    ResponseEntity<List<Visit>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Visit> findById(@PathVariable long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "type")
    ResponseEntity<List<Visit>> findAllByTypeOfVisit(@RequestParam String type){
        List<Visit> visits = repository.findAllByType(type);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @GetMapping(params = "deadline")
    ResponseEntity<List<Visit>> findAllByDeadline(@RequestParam LocalDateTime deadline){
        List<Visit> visits = repository.findAllByDeadline(deadline);
        if(visits.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(visits);
    }

    @PostMapping
    ResponseEntity<Visit> addVisit(@RequestBody Visit visit){
        Visit result = repository.save(visit);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping
    ResponseEntity<?> updateVisit(@RequestBody Visit visit){
        if(repository.existsById(visit.getId())){
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
