package com.github.konradcz2001.medicalappointments.client.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

/**
 * This code snippet represents a Java class called ClientDTOMapper, which is a service class used for mapping between Client and ClientDTO objects.
 * It implements the DTOMapper interface.
 * <p>
 * The ClientDTOMapper class provides the following methods:
 * - mapToDTO(Client source): maps a Client object to a ClientDTO object
 * - mapFromDTO(ClientDTO sourceDTO, Client target): maps a ClientDTO object to a Client object
 * - mapToClientReviewDTO(Review review): maps a Review object to a ClientReviewDTO object
 * <p>
 * Note: The ClientDTOMapper class is annotated with @Service to indicate that it is a Spring service component.
 */
@Service
public class ClientDTOMapper implements DTOMapper <ClientDTO, Client> {


    @Override
    public ClientDTO mapToDTO(Client source) {
        return new ClientDTO(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.getRole()
        );
    }

    @Override
    public Client mapFromDTO(ClientDTO sourceDTO, Client target) {
        target.setFirstName(sourceDTO.firstName());
        target.setLastName(sourceDTO.lastName());
        return target;
    }

    public ClientReviewDTO mapToClientReviewDTO(Review review) {
        return new ClientReviewDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getDoctor().getId()
        );
    }

}
