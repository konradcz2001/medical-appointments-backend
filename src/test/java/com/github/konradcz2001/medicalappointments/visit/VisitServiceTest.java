package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {
    @Mock
    private VisitRepository repository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private ClientRepository clientRepository;
    @Spy
    private VisitDTOMapper dtoMapper;
    @InjectMocks
    private VisitService underTest;


    @Test
    void shouldCreateVisit() {
        // Arrange
        VisitDTO visit = spy(new VisitDTO(null, null, "consultation", null, null, null, null));


        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));
        when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var response = underTest.createVisit(visit);

        // Assert
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(VisitDTO.class, response.getBody().getClass());
        assertEquals("consultation", response.getBody().type());

        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(repository).save(visitCaptor.capture());
        assertNull(visitCaptor.getValue().getId());
        assertEquals(Doctor.class, visitCaptor.getValue().getDoctor().getClass());
        assertEquals(Client.class, visitCaptor.getValue().getClient().getClass());

    }

    @Test
    void shouldFindVisitById() {
        // Arrange
        Visit visit2 = new Visit();
        visit2.setId(2L);
        visit2.setDoctor(new Doctor());
        visit2.setClient(new Client());

        when(repository.findById(2L)).thenReturn(Optional.of(visit2));

        // Act
        var response = underTest.readById(2L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(VisitDTO.class, response.getBody().getClass());
        assertEquals(2, response.getBody().id());
    }

    @Test
    void shouldUpdateVisit() {
        // Arrange
        Long id = 1L;
        Visit original = new Visit();
        original.setId(id);
        original.setDoctor(new Doctor());
        original.setClient(new Client());
        VisitDTO toUpdate = new VisitDTO(2L, LocalDateTime.now(), "consultation", "notes", BigDecimal.valueOf(1d), 1L, 1L);

        when(repository.findById(id)).thenReturn(Optional.of(original));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var response = underTest.updateVisit(id, toUpdate);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(repository).save(visitCaptor.capture());
        Visit visit = visitCaptor.getValue();

        assertEquals(1, visit.getId());
        assertEquals("consultation", visit.getType());
        assertEquals("notes", visit.getNotes());
        assertEquals(BigDecimal.valueOf(1d), visit.getPrice());
        assertNull(visit.getDoctor().getId());
        assertNull(visit.getClient().getId());
        assertNotNull(visit.getDate());
    }

    @Test
    void shouldDeleteVisitById() {
        // Arrange
        Long id = 1L;
        Visit visit = new Visit();
        when(repository.findById(id)).thenReturn(Optional.of(visit));

        // Act
        var response = underTest.deleteVisit(id);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).deleteById(id);
    }

}