package com.github.konradcz2001.medicalappointments.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/visits")
@CrossOrigin
class VisitController {
    private final VisitService service;

    VisitController(final VisitService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Page<Visit>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<Visit> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "type")
    ResponseEntity<Page<Visit>> readAllByTypeOfVisit(@RequestParam String type, Pageable pageable){
        return service.readAllByTypeOfVisit(type, pageable);
    }

    @GetMapping(params = "after")
    ResponseEntity<Page<Visit>> readAllByDateOfVisitAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByDateOfVisitAfter(after, pageable);
    }

    @GetMapping(params = "before")
    ResponseEntity<Page<Visit>> readAllByDateOfVisitBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBefore(before, pageable);
    }

    @GetMapping("/between")
    ResponseEntity<Page<Visit>> readAllByDateOfVisitBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBetween(after, before, pageable);
    }

    //TODO params
    @GetMapping(params = "doctorId")
    ResponseEntity<Page<Visit>> readAllByDoctorId(@RequestParam Long doctorId, Pageable pageable){
        return service.readAllByDoctorId(doctorId, pageable);
    }

    @GetMapping(params = "clientId")
    ResponseEntity<Page<Visit>> readAllByClientId(@RequestParam Long clientId, Pageable pageable){
        return service.readAllByClientId(clientId, pageable);
    }

    @GetMapping(params = "price")
    ResponseEntity<Page<Visit>> readAllByPrice(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPrice(price, pageable);
    }

    @GetMapping(params = "price1")
    ResponseEntity<Page<Visit>> readAllByPriceLessThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceLessThan(price, pageable);
    }

    @GetMapping(params = "price2")
    ResponseEntity<Page<Visit>> readAllByPriceGreaterThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceGreaterThan(price, pageable);
    }


    @PostMapping
    ResponseEntity<?> createVisit(@RequestBody Visit visit){
        return service.createVisit(visit);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateVisit(@PathVariable Long id, @RequestBody Visit visit){
        return service.updateVisit(id, visit);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVisit(@PathVariable Long id){
        return service.deleteVisit(id);
    }

}
