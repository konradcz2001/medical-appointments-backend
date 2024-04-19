package com.github.konradcz2001.medicalappointments.specialization;

import com.github.konradcz2001.medicalappointments.exception.WrongSpecializationException;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTO;
import com.github.konradcz2001.medicalappointments.specialization.DTO.SpecializationDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecializationServiceTest {
    @Mock
    private SpecializationRepository repository;
    @Spy
    private SpecializationDTOMapper dtoMapper;
    @InjectMocks
    private SpecializationService underTest;

    @Test
    void shouldFindSpecializationById() {
        // given
        Specialization specialization2 = new Specialization();
        specialization2.setId(2);

        when(repository.findById(2)).thenReturn(Optional.of(specialization2));

        // when
        var response = underTest.readById(2);

        // then
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(SpecializationDTO.class, response.getBody().getClass());
        assertEquals(2, response.getBody().id());
    }

    @Test
    void shouldFindSpecializationBySpecialization() {
        // given
        Specialization specialization1 = new Specialization();
        Specialization specialization2 = new Specialization();
        specialization1.setSpecialization("spec1");
        specialization2.setSpecialization("spec2");

        when(repository.findFirstBySpecialization("spec1")).thenReturn(Optional.of(specialization1));

        // when
        var response = underTest.readBySpecialization("spec1");

        // then
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(SpecializationDTO.class, response.getBody().getClass());
        assertEquals("spec1", response.getBody().specialization());
    }

    @Test
    void shouldThrownWrongSpecializationExceptionThatNotExist() {
        // given
        String spec = "spec8";
        when(repository.findFirstBySpecialization(spec)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest.readBySpecialization(spec))
                .isInstanceOf(WrongSpecializationException.class)
                .hasMessageContaining(spec) ;
    }

    @Test
    void shouldCreateNewSpecialization() {
        // given
        Specialization specialization = spy(new Specialization());
        specialization.setId(1);
        specialization.setSpecialization("spec");

        when(repository.existsBySpecialization(specialization.getSpecialization())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));


        // when
        var response = underTest.createSpecialization(specialization);

        // then
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(SpecializationDTO.class, response.getBody().getClass());
        assertEquals("spec", response.getBody().specialization());
        verify(specialization).setId(null);
        verify(specialization).setDoctors(new HashSet<>());

    }

    @Test
    void shouldThrowWrongSpecializationExceptionWhileCreatingNewSpecializationBecauseAlreadyExist() {
        // given
        Specialization specialization = new Specialization();

        when(repository.existsBySpecialization(specialization.getSpecialization())).thenReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.createSpecialization(specialization))
                .isInstanceOf(WrongSpecializationException.class)
                .hasMessageContaining("already exist") ;
        verify(repository, never()).save(any());

    }

    @Test
    void shouldUpdateSpecializationById() {
        // given
        Integer id = 2;
        Specialization specialization1 = new Specialization();
        Specialization specialization2 = new Specialization();
        specialization1.setId(1);
        specialization2.setId(id);

        SpecializationDTO toUpdate = new SpecializationDTO(3, "spec");

        when(repository.findById(id)).thenReturn(Optional.of(specialization2));

        // when
        var response = underTest.updateSpecialization(id, toUpdate);

        // then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        ArgumentCaptor<Specialization> specCaptor = ArgumentCaptor.forClass(Specialization.class);
        verify(repository).save(specCaptor.capture());
        Specialization spec = specCaptor.getValue();

        assertEquals(2, spec.getId());
        assertEquals("spec", spec.getSpecialization());
    }

    @Test
    void shouldDeleteSpecializationById() {
        // given
        Integer id = 1;
        Specialization specialization1 = new Specialization();
        specialization1.setId(1);

        when(repository.findById(id)).thenReturn(Optional.of(specialization1));

        // when
        var response = underTest.deleteSpecialization(id);

        // then
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).deleteById(id);
    }


}