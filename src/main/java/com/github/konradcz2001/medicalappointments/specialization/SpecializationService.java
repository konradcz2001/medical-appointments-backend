package com.github.konradcz2001.medicalappointments.specialization;

import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTOMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashSet;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.SPECIALIZATION;

/**
 * Service class for managing specializations.
 * This class provides methods for retrieving, creating, updating, and deleting specializations.
 */
@Service
class SpecializationService {
    private final SpecializationRepository repository;
    private final SpecializationDTOMapper dtoMapper;

    SpecializationService(final SpecializationRepository repository, SpecializationDTOMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }


    /**
     * Retrieves all specializations.
     * <p>
     * This method retrieves all specializations from the repository.
     * It uses the 'findAll' method of the repository to retrieve all specializations.
     * The retrieved specializations are then mapped to SpecializationDTO objects using the 'mapToDTO' method of the dtoMapper.
     * The mapped specializations are wrapped in a ResponseEntity with status code OK and returned.
     *
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of SpecializationDTO objects
     */
    ResponseEntity<Page<SpecializationDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }


    /**
     * Retrieves a specialization by its ID.
     * <p>
     * This method retrieves a specialization from the repository based on its ID.
     * It uses the 'findById' method of the repository to find the specialization with the given ID.
     * If a specialization is found, it is mapped to a SpecializationDTO object using the 'mapToDTO' method of the dtoMapper.
     * The mapped specialization is then wrapped in a ResponseEntity with status code OK and returned.
     * If no specialization is found, a ResourceNotFoundException is thrown with an appropriate error message.
     *
     * @param id the ID of the specialization to retrieve
     * @return a ResponseEntity containing the retrieved specialization as a SpecializationDTO object
     * @throws ResourceNotFoundException if the specialization with the given ID does not exist
     */
    ResponseEntity<SpecializationDTO> readById(Integer id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }

    /**
     * Retrieves a specialization by its name.
     * <p>
     * This method retrieves a specialization from the repository based on its name.
     * It uses the 'findFirstBySpecialization' method of the repository to find the specialization with the given name.
     * If a specialization is found, it is mapped to a SpecializationDTO object using the 'mapToDTO' method of the dtoMapper.
     * The mapped specialization is then wrapped in a ResponseEntity with status code OK and returned.
     * If no specialization is found, a WrongSpecializationException is thrown with an appropriate error message.
     *
     * @param specialization the name of the specialization to retrieve
     * @return a ResponseEntity containing the retrieved specialization as a SpecializationDTO object
     * @throws WrongSpecializationException if the specialization with the given name does not exist
     */
    ResponseEntity<SpecializationDTO> readBySpecialization(String specialization){
        return repository.findFirstBySpecialization(specialization)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new WrongSpecializationException("Specialization: " + specialization + "does not exist"));
    }


    /**
     * Creates a new specialization.
     * <p>
     * This method creates a new specialization with the given specialization object.
     * It checks if the specialization already exists in the repository and throws a WrongSpecializationException if it does.
     * The ID of the specialization is set to null and the doctors associated with the specialization are initialized as an empty set.
     * The specialization is then saved in the repository and the created specialization is returned as a SpecializationDTO object.
     *
     * @param specialization the specialization object to create
     * @return a ResponseEntity containing the created specialization as a SpecializationDTO object
     * @throws WrongSpecializationException if the specialization already exists
     */
    ResponseEntity<SpecializationDTO> createSpecialization(Specialization specialization){
        if(repository.existsBySpecialization(specialization.getSpecialization()))
            throw new WrongSpecializationException("Specialization: " + specialization.getSpecialization() + " already exist");

        specialization.setId(null);
        specialization.setDoctors(new HashSet<>());
        Specialization created = repository.save(specialization);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }

    /**
     * Updates a specialization with the given ID.
     *
     * @param id       the ID of the specialization to update
     * @param toUpdate a SpecializationDTO object representing the updated specialization data
     * @return a ResponseEntity with no content if the specialization is successfully updated
     * @throws ResourceNotFoundException if the specialization with the given ID is not found
     */
    ResponseEntity<?> updateSpecialization(Integer id, SpecializationDTO toUpdate){
        return repository.findById(id)
                .map(spec -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, spec));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }

    /**
     * Deletes a specialization with the given ID.
     *
     * @param id the ID of the specialization to delete
     * @return a ResponseEntity with no content if the specialization is successfully deleted
     * @throws ResourceNotFoundException if the specialization with the given ID is not found
     */
    ResponseEntity<?> deleteSpecialization(Integer id){
        return repository.findById(id)
                .map(spec -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(SPECIALIZATION, id.longValue()));
    }
}
