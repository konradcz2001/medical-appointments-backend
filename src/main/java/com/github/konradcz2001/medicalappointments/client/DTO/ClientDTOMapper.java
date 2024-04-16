package com.github.konradcz2001.medicalappointments.client.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

@Service
public class ClientDTOMapper implements DTOMapper <ClientResponseDTO, Client> {

    @Override
    public ClientResponseDTO apply(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getRole()
        );
    }

    public ClientReviewResponseDTO applyForReview(Review review) {
        return new ClientReviewResponseDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getDoctor().getId()
        );
    }

}
