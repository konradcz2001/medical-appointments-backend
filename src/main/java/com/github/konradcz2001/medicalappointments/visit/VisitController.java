package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import jakarta.validation.Valid;
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
    ResponseEntity<Page<VisitDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<VisitDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "type")
    ResponseEntity<Page<VisitDTO>> readAllByTypeOfVisit(@RequestParam String type, Pageable pageable){
        return service.readAllByTypeOfVisit(type, pageable);
    }

    @GetMapping(params = "after")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByDateOfVisitAfter(after, pageable);
    }

    @GetMapping(params = "before")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBefore(before, pageable);
    }

    @GetMapping("/between")
    ResponseEntity<Page<VisitDTO>> readAllByDateOfVisitBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBetween(after, before, pageable);
    }

    //TODO params
    @GetMapping(params = "doctorId")
    ResponseEntity<Page<VisitDTO>> readAllByDoctorId(@RequestParam Long doctorId, Pageable pageable){
        return service.readAllByDoctorId(doctorId, pageable);
    }

    @GetMapping(params = "clientId")
    ResponseEntity<Page<VisitDTO>> readAllByClientId(@RequestParam Long clientId, Pageable pageable){
        return service.readAllByClientId(clientId, pageable);
    }

    @GetMapping(params = "price")
    ResponseEntity<Page<VisitDTO>> readAllByPrice(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPrice(price, pageable);
    }

    @GetMapping(params = "maxPrice")
    ResponseEntity<Page<VisitDTO>> readAllByPriceLessThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceLessThan(price, pageable);
    }

    @GetMapping(params = "minPrice")
    ResponseEntity<Page<VisitDTO>> readAllByPriceGreaterThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceGreaterThan(price, pageable);
    }


    @PostMapping
    ResponseEntity<VisitDTO> createVisit(@Valid @RequestBody VisitDTO visit){
        return service.createVisit(visit);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateVisit(@PathVariable Long id, @Valid @RequestBody VisitDTO visit){
        return service.updateVisit(id, visit);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVisit(@PathVariable Long id){
        return service.deleteVisit(id);
    }

}
