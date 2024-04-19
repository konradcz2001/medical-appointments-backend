package com.github.konradcz2001.medicalappointments.specialization;

import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTOMapper;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashSet;

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



    ResponseEntity<Page<SpecializationDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }


    ResponseEntity<SpecializationDTO> readById(Integer id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }


    ResponseEntity<SpecializationDTO> readBySpecialization(String specialization){
        return repository.findFirstBySpecialization(specialization)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongSpecializationException("Specialization: " + specialization + "does not exist"));
    }


    ResponseEntity<SpecializationDTO> createSpecialization(Specialization specialization){
        if(repository.existsBySpecialization(specialization.getSpecialization()))
            throw new WrongSpecializationException("Specialization: " + specialization.getSpecialization() + " already exist");

        specialization.setId(null);
        specialization.setDoctors(new HashSet<>());
        Specialization created = repository.save(specialization);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }


    ResponseEntity<?> updateSpecialization(Integer id, SpecializationDTO toUpdate){
        return repository.findById(id)
                .map(spec -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, spec));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }


    ResponseEntity<?> deleteSpecialization(Integer id){
        return repository.findById(id)
                .map(spec -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }
}
