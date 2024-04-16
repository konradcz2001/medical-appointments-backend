package com.github.konradcz2001.medicalappointments.specialization.specialization;

import com.github.konradcz2001.medicalappointments.specialization.specialization.DTO.SpecializationDTOMapper;
import com.github.konradcz2001.medicalappointments.specialization.specialization.DTO.SpecializationResponseDTO;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.SPECIALIZATION;

@Service
class SpecializationService {
    private final SpecializationRepository repository;
    private final SpecializationDTOMapper dtoMapper;

    SpecializationService(final SpecializationRepository repository, SpecializationDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }



    ResponseEntity<Page<SpecializationResponseDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }


    ResponseEntity<SpecializationResponseDTO> readById(Integer id){
        return repository.findById(id)
                .map(dtoMapper::apply)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }


    ResponseEntity<SpecializationResponseDTO> readBySpecialization(String specialization){
        return repository.findFirstBySpecialization(specialization)
                .map(dtoMapper::apply)
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
