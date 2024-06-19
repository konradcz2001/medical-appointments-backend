package com.github.konradcz2001.medicalappointments.visit;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.doctor.schedule.Schedule;
import com.github.konradcz2001.medicalappointments.exception.exceptions.ResourceNotFoundException;
import com.github.konradcz2001.medicalappointments.exception.exceptions.WrongVisitException;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.visit.DTO.TypeOfVisitDTO;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTO;
import com.github.konradcz2001.medicalappointments.visit.DTO.VisitDTOMapper;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
        Long doctorId = 1L;
        Long clientId = 2L;
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, LocalDateTime.of(3000,1,1,10,1,1), "consultation", typeOfVisitDTO, false, clientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setLeaves(List.of());
        doctor.setSchedule(new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        Client client = new Client();
        client.setId(clientId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(repository.save(any(Visit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<VisitDTO> response = underTest.createVisit(visitDTO);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("consultation", response.getBody().notes());

        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(repository).save(visitCaptor.capture());
        assertEquals(client, visitCaptor.getValue().getClient());
        assertEquals(doctor, visitCaptor.getValue().getTypeOfVisit().getDoctor());
    }

    @Test
    void shouldThrowExceptionWhenVisitConflictsWithLeaves() {
        // Arrange
        Long doctorId = 1L;
        Long clientId = 2L;
        LocalDateTime visitDate = LocalDateTime.now().plusDays(1);
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, visitDate, "consultation", typeOfVisitDTO, false, clientId);
        Doctor doctor = new Doctor();
        doctor.setLeaves(List.of(new Leave(1L, visitDate.minusHours(1), visitDate.plusHours(1), doctor)));

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Act & Assert
        assertThrows(WrongVisitException.class, () -> underTest.createVisit(visitDTO));
    }

    @Test
    void shouldThrowExceptionWhenVisitConflictsWithAnotherVisit() {
        // Arrange
        Long doctorId = 1L;
        Long clientId = 2L;
        LocalDateTime visitDate = LocalDateTime.now().plusDays(1);
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, visitDate, "consultation", typeOfVisitDTO, false, clientId);
        Doctor doctor = new Doctor();
        Visit existingVisit = new Visit();
        existingVisit.setDate(visitDate);
        existingVisit.setTypeOfVisit(new TypeOfVisit(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctor));

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));
        when(repository.findAllByTypeOfVisit_Doctor_Id(doctorId)).thenReturn(List.of(existingVisit));

        // Act & Assert
        assertThrows(WrongVisitException.class, () -> underTest.createVisit(visitDTO));
    }

    @Test
    void shouldThrowExceptionWhenVisitConflictsWithSchedule() {
        // Arrange
        Long doctorId = 1L;
        Long clientId = 2L;
        LocalDateTime visitDate = LocalDateTime.now().withHour(3);
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, visitDate, "consultation", typeOfVisitDTO, false, clientId);
        Doctor doctor = new Doctor();
        doctor.setSchedule(new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0)));

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Act & Assert
        assertThrows(WrongVisitException.class, () -> underTest.createVisit(visitDTO));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionForInvalidDoctor() {
        // Arrange
        Long doctorId = 1L;
        Long clientId = 2L;
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, LocalDateTime.now(), "consultation", typeOfVisitDTO, false, clientId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.createVisit(visitDTO));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionForInvalidClient() {
        // Arrange
        Long doctorId = 1L;
        Long clientId = 2L;
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, doctorId);
        VisitDTO visitDTO = new VisitDTO(null, LocalDateTime.now(), "consultation", typeOfVisitDTO, false, clientId);
        Doctor doctor = new Doctor();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.createVisit(visitDTO));
    }

    @Test
    void shouldReadAllVisits() {
        // Arrange
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAll(pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAll(pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadVisitById() {
        // Arrange
        Long visitId = 1L;
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setTypeOfVisit(typeOfVisit);
        visit.setClient(client);

        when(repository.findById(visitId)).thenReturn(Optional.of(visit));

        // Act
        ResponseEntity<VisitDTO> response = underTest.readById(visitId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(visitId, response.getBody().id());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenReadingNonExistentVisitById() {
        // Arrange
        Long visitId = 1L;

        when(repository.findById(visitId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.readById(visitId));
    }

    @Test
    void shouldUpdateVisit() {
        // Arrange
        Long visitId = 1L;
        Visit visit = new Visit();
        visit.setId(visitId);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        visit.setTypeOfVisit(typeOfVisit);
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, 1L);
        VisitDTO visitDTO = new VisitDTO(visitId, LocalDateTime.now(), "updated notes", typeOfVisitDTO, false, 2L);

        when(repository.findById(visitId)).thenReturn(Optional.of(visit));
        when(repository.save(any(Visit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<?> response = underTest.updateVisit(visitId, visitDTO);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(repository).save(visitCaptor.capture());
        assertEquals("updated notes", visitCaptor.getValue().getNotes());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentVisit() {
        // Arrange
        Long visitId = 1L;
        TypeOfVisitDTO typeOfVisitDTO = new TypeOfVisitDTO(1L, "consultation", BigDecimal.TEN, "USD", 30, true, 1L);
        VisitDTO visitDTO = new VisitDTO(visitId, LocalDateTime.now(), "updated notes", typeOfVisitDTO, false, 2L);

        when(repository.findById(visitId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.updateVisit(visitId, visitDTO));
    }

    @Test
    void shouldDeleteVisit() {
        // Arrange
        Long visitId = 1L;
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setTypeOfVisit(new TypeOfVisit());

        when(repository.findById(visitId)).thenReturn(Optional.of(visit));

        // Act
        ResponseEntity<?> response = underTest.deleteVisit(visitId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(repository).deleteById(visitId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistentVisit() {
        // Arrange
        Long visitId = 1L;

        when(repository.findById(visitId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.deleteVisit(visitId));
    }

    @Test
    void shouldCancelVisit() {
        // Arrange
        Long visitId = 1L;
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setTypeOfVisit(new TypeOfVisit());

        when(repository.findById(visitId)).thenReturn(Optional.of(visit));
        when(repository.save(any(Visit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<?> response = underTest.cancelVisit(visitId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(repository).save(visitCaptor.capture());
        assertTrue(visitCaptor.getValue().isCancelled());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCancellingNonExistentVisit() {
        // Arrange
        Long visitId = 1L;

        when(repository.findById(visitId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> underTest.cancelVisit(visitId));
    }

    @Test
    void shouldReadAllVisitsByTypeOfVisit() {
        // Arrange
        String type = "consultation";
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setType(type);
        typeOfVisit.setId(1L);
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisitTypeContainingIgnoreCase(type, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByTypeOfVisit(type, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByDateOfVisitAfter() {
        // Arrange
        LocalDateTime after = LocalDateTime.now().minusDays(1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByDateAfter(after, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByDateOfVisitAfter(after, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByDateOfVisitBefore() {
        // Arrange
        LocalDateTime before = LocalDateTime.now().plusDays(1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByDateBefore(before, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByDateOfVisitBefore(before, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByDateOfVisitBetween() {
        // Arrange
        LocalDateTime after = LocalDateTime.now().minusDays(1);
        LocalDateTime before = LocalDateTime.now().plusDays(1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByDateAfterAndDateBefore(after, before, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByDateOfVisitBetween(after, before, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisit_Doctor_Id(doctorId, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByDoctorId(doctorId, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByDoctorIdAndCancellationStatus() {
        // Arrange
        Long doctorId = 1L;
        boolean isCancelled = false;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setCancelled(isCancelled);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setCancelled(isCancelled);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisit_Doctor_IdAndIsCancelled(doctorId, isCancelled, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByDoctorIdAndCancellationStatus(doctorId, isCancelled, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByClientId() {
        // Arrange
        Long clientId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByClientId(clientId, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByClientId(clientId, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByClientIdAndCancellationStatus() {
        // Arrange
        Long clientId = 1L;
        boolean isCancelled = false;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(BigDecimal.TEN);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setCancelled(isCancelled);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setCancelled(isCancelled);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByClientIdAndIsCancelled(clientId, isCancelled, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByClientIdAndCancellationStatus(clientId, isCancelled, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByPrice() {
        // Arrange
        BigDecimal price = BigDecimal.TEN;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(price);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisitPrice(price, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByPrice(price, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByPriceLessThan() {
        // Arrange
        BigDecimal price = BigDecimal.TEN;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(price);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisitPriceLessThanEqual(price, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByPriceLessThan(price, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    void shouldReadAllVisitsByPriceGreaterThan() {
        // Arrange
        BigDecimal price = BigDecimal.TEN;
        PageRequest pageRequest = PageRequest.of(0, 10);
        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setId(1L);
        typeOfVisit.setType("consultation");
        typeOfVisit.setPrice(price);
        typeOfVisit.setCurrency("USD");
        typeOfVisit.setDuration(30);
        typeOfVisit.setActive(true);
        typeOfVisit.setDoctor(new Doctor());
        Client client = new Client();
        client.setId(1L);

        Visit visit1 = new Visit();
        visit1.setTypeOfVisit(typeOfVisit);
        visit1.setClient(client);

        Visit visit2 = new Visit();
        visit2.setTypeOfVisit(typeOfVisit);
        visit2.setClient(client);

        Page<Visit> visits = new PageImpl<>(List.of(visit1, visit2));

        when(repository.findAllByTypeOfVisitPriceGreaterThanEqual(price, pageRequest)).thenReturn(visits);

        // Act
        ResponseEntity<Page<VisitDTO>> response = underTest.readAllByPriceGreaterThan(price, pageRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }
}
