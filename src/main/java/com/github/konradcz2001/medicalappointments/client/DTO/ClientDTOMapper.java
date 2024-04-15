package com.github.konradcz2001.medicalappointments.client.DTO;


import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.review.Review;

public class ClientDTOMapper {

    public static ClientResponseDTO apply(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getRole()
        );
    }

    public static ClientReviewResponseDTO applyForReview(Review review) {
        return new ClientReviewResponseDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getDoctor().getId()
        );
    }

}
