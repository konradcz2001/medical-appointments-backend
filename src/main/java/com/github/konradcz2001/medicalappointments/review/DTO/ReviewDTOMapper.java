package com.github.konradcz2001.medicalappointments.review.DTO;


import com.github.konradcz2001.medicalappointments.review.Review;

public class ReviewDTOMapper {

    public static ReviewResponseDTO apply(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getDate(),
                review.getRating(),
                review.getDescription(),
                review.getDoctor().getId(),
                review.getClient().getId()
        );
    }


}
