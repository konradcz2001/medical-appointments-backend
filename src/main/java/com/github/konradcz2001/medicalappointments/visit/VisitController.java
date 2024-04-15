package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.visit.DTO.VisitResponseDTO;
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
    ResponseEntity<Page<VisitResponseDTO>> readAll(Pageable pageable){
        return service.readAll(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<VisitResponseDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @GetMapping(params = "type")
    ResponseEntity<Page<VisitResponseDTO>> readAllByTypeOfVisit(@RequestParam String type, Pageable pageable){
        return service.readAllByTypeOfVisit(type, pageable);
    }

    @GetMapping(params = "after")
    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitAfter(@RequestParam LocalDateTime after, Pageable pageable){
        return service.readAllByDateOfVisitAfter(after, pageable);
    }

    @GetMapping(params = "before")
    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitBefore(@RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBefore(before, pageable);
    }

    @GetMapping("/between")
    ResponseEntity<Page<VisitResponseDTO>> readAllByDateOfVisitBetween(@RequestParam LocalDateTime after, @RequestParam LocalDateTime before, Pageable pageable){
        return service.readAllByDateOfVisitBetween(after, before, pageable);
    }

    //TODO params
    @GetMapping(params = "doctorId")
    ResponseEntity<Page<VisitResponseDTO>> readAllByDoctorId(@RequestParam Long doctorId, Pageable pageable){
        return service.readAllByDoctorId(doctorId, pageable);
    }

    @GetMapping(params = "clientId")
    ResponseEntity<Page<VisitResponseDTO>> readAllByClientId(@RequestParam Long clientId, Pageable pageable){
        return service.readAllByClientId(clientId, pageable);
    }

    @GetMapping(params = "price")
    ResponseEntity<Page<VisitResponseDTO>> readAllByPrice(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPrice(price, pageable);
    }

    @GetMapping(params = "price1")
    ResponseEntity<Page<VisitResponseDTO>> readAllByPriceLessThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceLessThan(price, pageable);
    }

    @GetMapping(params = "price2")
    ResponseEntity<Page<VisitResponseDTO>> readAllByPriceGreaterThan(@RequestParam BigDecimal price, Pageable pageable){
        return service.readAllByPriceGreaterThan(price, pageable);
    }


    @PostMapping
    ResponseEntity<?> createVisit(@Valid @RequestBody Visit visit){
        return service.createVisit(visit);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateVisit(@PathVariable Long id, @Valid @RequestBody Visit visit){
        return service.updateVisit(id, visit);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVisit(@PathVariable Long id){
        return service.deleteVisit(id);
    }

}
