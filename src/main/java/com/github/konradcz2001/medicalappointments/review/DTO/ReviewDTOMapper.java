package com.github.konradcz2001.medicalappointments.review.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewDTOMapper implements DTOMapper<ReviewDTO, Review> {

    @Override
    public ReviewDTO mapToDTO(Review source) {
        return new ReviewDTO(
                source.getId(),
                source.getDate(),
                source.getRating(),
                source.getDescription(),
                source.getDoctor().getId(),
                source.getClient().getId()
        );
    }

    @Override
    public Review mapFromDTO(ReviewDTO sourceDTO, Review target) {
        target.setDescription(sourceDTO.description());
        target.setRating(sourceDTO.rating());

        return target;
    }


}
