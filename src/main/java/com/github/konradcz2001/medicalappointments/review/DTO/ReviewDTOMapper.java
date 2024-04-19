package com.github.konradcz2001.medicalappointments.review.DTO;


import com.github.konradcz2001.medicalappointments.common.DTOMapper;
import com.github.konradcz2001.medicalappointments.review.Review;
import org.springframework.stereotype.Service;

/**
 * This is a Java class named "ReviewDTOMapper" that implements the "DTOMapper" interface.
 * <p>
 * The ReviewDTOMapper class provides methods to map a Review object to a ReviewDTO object and vice versa.
 * The mapToDTO method takes a Review object as input and returns a ReviewDTO object with the corresponding properties.
 * The mapFromDTO method takes a ReviewDTO object and a Review object as input and sets the properties of the Review object based on the values in the ReviewDTO object.
 * <p>
 * Note: The ReviewDTOMapper class is annotated with the @Service annotation to indicate that it is a Spring service component.
 */
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
