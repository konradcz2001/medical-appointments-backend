package com.github.konradcz2001.medicalappointments.review;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository repository;
    @Spy
    private ReviewDTOMapper dtoMapper;
    @InjectMocks
    private ReviewService underTest;

    @Test
    void shouldFindReviewById() {
        // Arrange
        Review review2 = new Review();
        review2.setId(2L);
        review2.setDoctor(new Doctor());
        review2.setClient(new Client());

        when(repository.findById(2L)).thenReturn(Optional.of(review2));

        // Act
        var response = underTest.readById(2L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(ReviewDTO.class, response.getBody().getClass());
        assertEquals(2, response.getBody().id());
    }

}