package com.github.konradcz2001.medicalappointments.review.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewDTOMapper implements DTOMapper<ReviewResponseDTO, Review> {

    @Override
    public ReviewResponseDTO apply(Review review) {
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
