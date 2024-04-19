package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTO;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientDTOMapper;
import com.github.konradcz2001.medicalappointments.client.DTO.ClientReviewDTO;
import com.github.konradcz2001.medicalappointments.doctor.DTO.DoctorDTO;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.WrongReviewException;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTO;
import com.github.konradcz2001.medicalappointments.review.DTO.ReviewDTOMapper;
import com.github.konradcz2001.medicalappointments.review.Rating;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewRepository;
import com.github.konradcz2001.medicalappointments.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository repository;
    @Mock
    private ReviewRepository reviewRepository;
    @Spy
    private ClientDTOMapper dtoMapper;
    @Mock
    private DoctorRepository doctorRepository;
    @Spy
    private ReviewDTOMapper reviewDTOMapper;
    @InjectMocks
    private ClientService underTest;


    @Test
    void shouldFindClientById() {
        // given
        Client client2 = new Client();
        client2.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(client2));

        // when
        var response = underTest.readById(2L);

        // then
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(ClientDTO.class, response.getBody().getClass());
        assertEquals(2, response.getBody().id());
    }

    @Test
    void shouldCreateNewClient() {
        // given
        Client client = spy(new Client());
        client.setId(1L);
        client.setFirstName("name");

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));


        // when
        var response = underTest.createClient(client);

        // then
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(ClientDTO.class, response.getBody().getClass());
        assertEquals("name", response.getBody().firstName());
        verify(client).setReviews(new ArrayList<>());
        verify(client).setId(null);
    }


    @Test
    void shouldUpdateClient() {
        // given
        Long id = 1L;
        Client original = new Client();
        original.setId(id);
        ClientDTO toUpdate = new ClientDTO(2L, "name2", "lastname2", "email2", Role.CLIENT);

        when(repository.findById(id)).thenReturn(Optional.of(original));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        var response = underTest.updateClient(id, toUpdate);

        // then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(repository).save(clientCaptor.capture());
        Client client = clientCaptor.getValue();

        assertEquals(1, client.getId());
        assertEquals("name2", client.getFirstName());
        assertEquals("lastname2", client.getLastName());
        assertNull(client.getEmail());
        assertNull(client.getRole());
    }

    @Test
    void shouldDeleteClientById() {
        // given
        Long id = 1L;
        Client client1 = new Client();
        client1.setId(1L);
        when(repository.findById(id)).thenReturn(Optional.of(client1));

        // when
        var response = underTest.deleteClient(id);

        // then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).deleteById(id);
    }

    @Test
    void shouldAddReviewToClient() {
        //given
        Long clientId = 1L;
        Client client = spy(new Client());
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        Doctor doctor3 = new Doctor();
        client.setId(clientId);
        doctor1.setId(1L);
        doctor2.setId(2L);
        doctor3.setId(3L);

        Review review1 = new Review(1L, null, null, null, doctor1, client);
        Review review2 = new Review(2L, null, null, null, doctor2, client);
        ReviewDTO toAdd = new ReviewDTO(1L, null, null, null, 3L, 1L);

        client.addReview(review1);
        client.addReview(review2);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));
        when(doctorRepository.findById(3L)).thenReturn(Optional.of(doctor3));

        //when
        var response = underTest.addReview(clientId, toAdd);

        //then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).save(client);
        verify(doctorRepository).findById(3L);

        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
        //2 times in this test and 1 in tested method
        verify(client, times(3)).addReview(reviewCaptor.capture());
        List<Review> reviews = reviewCaptor.getAllValues();
        Review review = reviews.get(2);

        assertEquals(1, review.getClient().getId());
        assertEquals(3, review.getDoctor().getId());
        assertNotNull(review.getDate());
    }

    @Test
    void shouldThrowWrongReviewExceptionWhileAddingNewReviewToClient() {
        //given
        Long clientId = 1L;
        Client client = spy(new Client());
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        client.setId(clientId);
        doctor1.setId(1L);
        doctor2.setId(2L);

        Review review1 = new Review(1L, null, null, null, doctor1, client);
        Review review2 = new Review(2L, null, null, null, doctor2, client);
        ReviewDTO toAdd = new ReviewDTO(1L, null, null, null, 2L, 1L);

        client.addReview(review1);
        client.addReview(review2);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor2));

        //when
        //then
        assertThatThrownBy(() -> underTest.addReview(clientId, toAdd))
                .isInstanceOf(WrongReviewException.class)
                .hasMessageContaining("already has a review") ;
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowDoctorNotFoundExceptionWhileAddingNewReviewToClient() {
        //given
        Long clientId = 1L;
        Client client = spy(new Client());
        ReviewDTO toAdd = new ReviewDTO(1L, null, null, null, 2L, 1L);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.addReview(clientId, toAdd))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id") ;
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowClientNotFoundExceptionWhileAddingNewReviewToClient() {
        //given
        Long clientId = 1L;
        ReviewDTO toAdd = new ReviewDTO(1L, null, null, null, 2L, 2L);

        when(repository.findById(clientId)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.addReview(clientId, toAdd))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Client with id") ;
        verify(repository, never()).save(any());
    }

    @Test
    void shouldUpdateReview() {
        // given
        Long clientId = 1L;
        Client client = new Client();
        Review original = new Review();
        original.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        original.setDoctor(doctor);
        client.setId(clientId);
        client.addReview(original);

        ReviewDTO toUpdate = new ReviewDTO(1L, null, Rating.FIVE_STARS, "description", 5L, 3L);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        // when
        var response = underTest.updateReview(clientId, toUpdate);

        // then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(reviewCaptor.capture());
        Review review = reviewCaptor.getValue();

        assertEquals(1, review.getId());
        assertEquals(Rating.FIVE_STARS, review.getRating());
        assertEquals("description", review.getDescription());
        assertNotNull(review.getDate());
        assertEquals(doctor.getId(), review.getDoctor().getId());
        assertNull(review.getClient());
    }

    @Test
    void shouldThrownWrongReviewExceptionWhileUpdatingReview() {
        // given
        Long clientId = 1L;
        Client client = new Client();
        Review original = new Review();
        original.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        original.setDoctor(doctor);
        client.setId(clientId);
        client.addReview(original);

        ReviewDTO toUpdate = new ReviewDTO(2L, null, Rating.FIVE_STARS, "description", 5L, 3L);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        // when
        // then
        assertThatThrownBy(() -> underTest.updateReview(clientId, toUpdate))
                .isInstanceOf(WrongReviewException.class)
                .hasMessageContaining("does not have a review with id") ;
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void shouldRemoveClientsReview() {
        //given
        Long clientId = 1L;
        Long reviewId = 2L;
        Client client = new Client();
        client.setId(clientId);

        Review review1 = new Review(1L, null, null, null, null, client);
        Review review2 = new Review(2L, null, null, null, null, client);

        client.addReview(review1);
        client.addReview(review2);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        //when
        var response = underTest.removeReview(clientId, reviewId);

        //then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).save(client);
        assertEquals(1, client.getReviews().size());
    }


    @Test
    void shouldFindAllReviewByClient() {
        //given
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);

        Review review1 = new Review(1L, null, null, null, new Doctor(), client);
        Review review2 = new Review(2L, null, null, null, new Doctor(), client);

        client.addReview(review1);
        client.addReview(review2);

        Page<Review> reviews = new PageImpl<>(List.of(review1, review2));
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(reviewRepository.findAllByClientId(clientId, pageable)).thenReturn(reviews);

        //when
        var response = underTest.readAllReviews(clientId, pageable);

        //then
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(ClientReviewDTO.class, response.getBody().getContent().get(0).getClass());
    }

}