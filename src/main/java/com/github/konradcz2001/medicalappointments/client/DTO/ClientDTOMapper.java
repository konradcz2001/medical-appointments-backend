package com.github.konradcz2001.medicalappointments.client.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

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

    public ClientReviewDTO applyForReview(Review review) {
        return new ClientReviewDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getDoctor().getId()
        );
    }

}
