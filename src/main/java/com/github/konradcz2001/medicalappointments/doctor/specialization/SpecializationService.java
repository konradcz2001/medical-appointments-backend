package com.github.konradcz2001.medicalappointments.doctor.specialization;

import com.github.konradcz2001.medicalappointments.doctor.specialization.DTO.SpecializationDTOMapper;
import com.github.konradcz2001.medicalappointments.doctor.specialization.DTO.SpecializationResponseDTO;
import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.function.Supplier;

import static com.github.konradcz2001.medicalappointments.exception.MessageType.SPECIALIZATION;

@Service
class SpecializationService {
    private final SpecializationRepository repository;

    SpecializationService(final SpecializationRepository repository) {
        this.repository = repository;
    }

    //TODO get rid of it - one usage
    private ResponseEntity<Page<SpecializationResponseDTO>> returnResponse(Supplier<Page<Specialization>> suppliedSpecializations) {
        var specializations = suppliedSpecializations.get()
                .map(SpecializationDTOMapper::apply);
        if(specializations.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(specializations);
    }


    ResponseEntity<Page<SpecializationResponseDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable));
    }


    ResponseEntity<SpecializationResponseDTO> readById(Integer id){
        return repository.findById(id)
                .map(SpecializationDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }


    ResponseEntity<SpecializationResponseDTO> readBySpecialization(String specialization){
        return repository.findFirstBySpecialization(specialization)
                .map(SpecializationDTOMapper::apply)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());//TODO exception
    }


    ResponseEntity<?> createSpecialization(Specialization specialization){
        if(repository.existsBySpecialization(specialization.getSpecialization()))
            throw new WrongSpecializationException("Specialization: " + specialization.getSpecialization() + " already exist");

        Specialization result = repository.save(specialization);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


    ResponseEntity<?> updateSpecialization(Integer id, Specialization toUpdate){
        return repository.findById(id)
                .map(spec -> {
                    toUpdate.setId(id);
                    //TODO is it working? - updateSpecialization
                    toUpdate.setDoctors(spec.getDoctors());
                    return ResponseEntity.ok(repository.save(toUpdate));
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }


    ResponseEntity<?> deleteSpecialization(Integer id){
        return repository.findById(id)
                //TODO is it working? - deleteSpecialization
                .map(spec -> {
                    spec.getDoctors().forEach(doctor -> doctor.removeSpecialization(spec));
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }
}
