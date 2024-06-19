package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.doctor.DTO.*;
import com.github.konradcz2001.medicalappointments.doctor.schedule.Schedule;
import com.github.konradcz2001.medicalappointments.exception.exceptions.*;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.review.Review;
import com.github.konradcz2001.medicalappointments.review.ReviewRepository;
import com.github.konradcz2001.medicalappointments.security.Role;
import com.github.konradcz2001.medicalappointments.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.specialization.SpecializationRepository;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisitRepository;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    private  DoctorRepository repository;
    @Mock
    private SpecializationRepository specializationRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private LeaveRepository leaveRepository;
    @Mock
    private TypeOfVisitRepository typeOfVisitRepository;
    @Spy
    private DoctorDTOMapper dtoMapper;

    @InjectMocks
    private DoctorService underTest;



    @Test
    void shouldThrownDoctorNotFoundException() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        // Assert
        verify(repository, never()).save(any());
        assertThatThrownBy(() -> underTest.addLeave(1L, new DoctorLeaveDTO(null, null, null)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id = 1");

    }


    @Test
    void shouldThrownExceptionThatStartDateIsAfterEndDate() {
        // Arrange
        Doctor doctor = new Doctor();
        DoctorLeaveDTO toAdd = new DoctorLeaveDTO(1L, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(1));

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.addLeave(1L, toAdd))
                .isInstanceOf(WrongLeaveException.class)
                .hasMessage("The beginning of the leave cannot be later than the end");

    }

    @Test
    void shouldMergeLeavesWhenAddingNewLeave() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(3), doctor);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(7), doctor);
        Leave leave3 = new Leave(3L, now.plusDays(9), now.plusDays(11), doctor);

        doctor.addLeave(leave1);
        doctor.addLeave(leave2);
        doctor.addLeave(leave3);

        DoctorLeaveDTO toAdd = new DoctorLeaveDTO(4L, now.plusDays(6), now.plusDays(10));

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.addLeave(1L, toAdd);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(2, doctor.getLeaves().size());
        assertEquals(now.plusDays(1), doctor.getLeaves().get(0).getStartDate());
        assertEquals(now.plusDays(3), doctor.getLeaves().get(0).getEndDate());
        assertEquals(now.plusDays(5), doctor.getLeaves().get(1).getStartDate());
        assertEquals(now.plusDays(11), doctor.getLeaves().get(1).getEndDate());

    }

    @Test
    void shouldReplaceLeavesWithTheSameDatesWhenAddingNewLeave() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(3), doctor);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(7), doctor);
        Leave leave3 = new Leave(3L, now.plusDays(9), now.plusDays(11), doctor);

        doctor.addLeave(leave1);
        doctor.addLeave(leave2);
        doctor.addLeave(leave3);

        DoctorLeaveDTO toAdd = new DoctorLeaveDTO(4L, now.plusDays(5), now.plusDays(7));

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.addLeave(1L, toAdd);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(3, doctor.getLeaves().size());

    }

    @Test
    void shouldAddLeaveWithoutMerging() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(2), doctor);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(6), doctor);

        doctor.addLeave(leave1);
        doctor.addLeave(leave2);

        DoctorLeaveDTO toAdd = spy(new DoctorLeaveDTO(3L, now.plusDays(3), now.plusDays(4)));

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.addLeave(1L, toAdd);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(3, doctor.getLeaves().size());
        assertEquals(now.plusDays(3), doctor.getLeaves().get(2).getStartDate());
        assertEquals(now.plusDays(4), doctor.getLeaves().get(2).getEndDate());
    }

    @Test
    void shouldFindAllLeavesByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(2), null);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(6), null);

        Page<Leave> leaves = new PageImpl<>(List.of(leave1, leave2));
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(leaveRepository.findAllByDoctorId(doctorId, pageable)).thenReturn(leaves);

        // Act
        var response = underTest.readAllLeaves(doctorId, pageable);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().get(1).id());
        assertEquals(DoctorLeaveDTO.class, response.getBody().getContent().get(1).getClass());
    }

    @Test
    void shouldFindAllReviewsByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        Review review1 = new Review();
        Review review2 = new Review();
        review1.setClient(new Client());
        review2.setClient(new Client());
        review2.setId(2L);

        Page<Review> reviews = new PageImpl<>(List.of(review1, review2));
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(reviewRepository.findAllByDoctorId(doctorId, pageable)).thenReturn(reviews);

        // Act
        var response = underTest.readAllReviews(doctorId, pageable);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().get(1).id());
        assertEquals(DoctorReviewDTO.class, response.getBody().getContent().get(1).getClass());
    }

    @Test
    void shouldFindAllSpecializationsByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        Set<Specialization> specializations = Set.of(new Specialization(1, null, null), new Specialization(2, null, null));
        doctor.setSpecializations(specializations);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.readAllSpecializations(doctorId);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(DoctorSpecializationDTO.class, response.getBody().stream().findAny().get().getClass());
    }

    @Test
    void shouldAddAllNewSpecializationsByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = spy(new Doctor());

        Specialization s1 = new Specialization(1, null, null);
        Specialization s2 = new Specialization(2, null, null);
        Specialization s3 = new Specialization(3, null, null);
        Specialization s4 = new Specialization(4, null, null);
        List<Specialization> allSpecializations = List.of(s1, s2, s3, s4);

        doctor.addSpecialization(s2);
        doctor.addSpecialization(s3);

        Set<Integer> specializationsToAdd = Set.of(1, 4);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(specializationRepository.findById(any())).thenAnswer(invocation -> Optional.of(allSpecializations.get((int)invocation.getArgument(0) - 1)));

        // Act
        var response = underTest.addSpecializations(doctorId, specializationsToAdd);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(4, doctor.getSpecializations().size());
        verify(doctor, times(4)).addSpecialization(any());
        verify(repository).save(doctor);
    }

    @Test
    void shouldThrowSpecializationNotFoundExceptionWhenTryingToAddNewSet() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();

        Specialization s1 = new Specialization(1, null, null);
        Specialization s2 = new Specialization(2, null, null);
        Specialization s3 = new Specialization(3, null, null);
        Specialization s4 = new Specialization(4, null, null);
        List<Specialization> allSpecializations = List.of(s1, s2, s3, s4);

        doctor.addSpecialization(s2);
        doctor.addSpecialization(s3);

        Set<Integer> specializationsToAdd = Set.of(1, 4, 5);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(specializationRepository.findById(any())).thenAnswer(invocation ->
                Optional.ofNullable((int)invocation.getArgument(0) <= allSpecializations.size() ?
                        allSpecializations.get((int)invocation.getArgument(0) - 1) : null));

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.addSpecializations(doctorId, specializationsToAdd))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Specialization with id ");
        verify(repository, never()).save(any());

    }

    @Test
    void shouldThrowWrongSpecializationExceptionWhenTryingToAddNewSet_Max5SpecializationsPerDoctor() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();

        Specialization s1 = new Specialization(1, null, null);
        Specialization s2 = new Specialization(2, null, null);

        doctor.addSpecialization(s1);
        doctor.addSpecialization(s2);

        Set<Integer> specializationsToAdd = Set.of(3, 4, 5, 6);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.addSpecializations(doctorId, specializationsToAdd))
                .isInstanceOf(WrongSpecializationException.class)
                .hasMessageContaining("5 specializations");
        verify(repository, never()).save(any());

    }

    @Test
    void shouldRemoveLeave() {
        // Arrange
        Long leaveId = 2L;
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(2), doctor);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(6), doctor);

        doctor.addLeave(leave1);
        doctor.addLeave(leave2);

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.removeLeave(1L, leaveId);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(1, doctor.getLeaves().size());
        verify(repository).save(isA(Doctor.class));
    }

    @Test
    void shouldThrowWrongLeaveExceptionWhenRemovingLeave() {
        // Arrange
        Long leaveId = 3L;
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(2), doctor);
        Leave leave2 = new Leave(2L, now.plusDays(5), now.plusDays(6), doctor);

        doctor.addLeave(leave1);
        doctor.addLeave(leave2);

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.removeLeave(1L, leaveId))
                .isInstanceOf(WrongLeaveException.class)
                .hasMessageContaining("Doctor with id = " + 1 + " does not have the specified");
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrownResourceNotFoundExceptionWhenTryingToRemoveLeave() {
        // Arrange
        Long leaveId = 2L;
        Long doctorId = 2L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.removeLeave(doctorId, leaveId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id = " + doctorId) ;
        verify(repository, never()).save(any());
    }

    @Test
    void shouldRemoveSpecialization() {
        // Arrange
        Integer specializationId = 2;
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Specialization specialization1 = new Specialization(1, "spec1", Set.of(doctor));
        Specialization specialization2 = new Specialization(2, "spec2", Set.of(doctor));

        doctor.addSpecialization(specialization1);
        doctor.addSpecialization(specialization2);

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.removeSpecialization(1L, specializationId);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(1, doctor.getSpecializations().size());
        verify(repository).save(isA(Doctor.class));
    }

    @Test
    void shouldThrowWrongSpecializationExceptionWhenRemovingSpecialization() {
        // Arrange
        Integer specializationId = 3;
        LocalDateTime now = LocalDateTime.now();

        Doctor doctor = new Doctor();
        Specialization specialization1 = new Specialization(1, "spec1", Set.of(doctor));
        Specialization specialization2 = new Specialization(2, "spec2", Set.of(doctor));

        doctor.addSpecialization(specialization1);
        doctor.addSpecialization(specialization2);

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.removeSpecialization(1L, specializationId))
                .isInstanceOf(WrongSpecializationException.class)
                .hasMessageContaining("Doctor with id = " + 1 + " does not have the specified");
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrownResourceNotFoundExceptionWhenTryingToRemoveSpecialization() {
        // Arrange
        Integer specializationId = 2;
        Long doctorId = 2L;

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.removeSpecialization(doctorId, specializationId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id = " + doctorId);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldFindDoctorById() {
        // Arrange
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        doctor1.setId(1L);
        doctor2.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(doctor2));

        // Act
        var response = underTest.readById(2L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(DoctorDTO.class, response.getBody().getClass());
        assertEquals(2, response.getBody().id());
    }

    @Test
    void shouldCreateNewDoctor() {
        // Arrange
        Doctor doctor = spy(new Doctor());
        doctor.setId(1L);
        doctor.setFirstName("name");

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var response = underTest.createDoctor(doctor);

        // Assert
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(DoctorDTO.class, response.getBody().getClass());
        assertEquals("name", response.getBody().firstName());
        verify(doctor).setId(null);
        verify(doctor).setSpecializations(new HashSet<>());
        verify(doctor).setLeaves(new ArrayList<>());
        verify(doctor).setReviews(new ArrayList<>());
    }

    @Test
    void shouldUpdateDoctor() {
        // Arrange
        Long id = 1L;
        Doctor original = new Doctor();
        original.setId(id);
        DoctorDTO toUpdate = new DoctorDTO(2L, "name2", "lastname2", "email2", Role.DOCTOR,true, null, "description2", null, null, null, null);

        when(repository.findById(id)).thenReturn(Optional.of(original));

        // Act
        var response = underTest.updateDoctor(id, toUpdate);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        ArgumentCaptor<Doctor> doctorCaptor = ArgumentCaptor.forClass(Doctor.class);
        verify(repository).save(doctorCaptor.capture());
        Doctor doctor = doctorCaptor.getValue();

        assertEquals(1, doctor.getId());
        assertEquals("name2", doctor.getFirstName());
        assertEquals("description2", doctor.getProfileDescription());
        assertNull(doctor.getEmail());
        assertNull(doctor.getRole());
        assertFalse(doctor.isVerified());
    }

    @Test
    @Disabled
    void shouldFindAllDoctorAvailableByDate() {
        // Arrange
        LocalDateTime date = LocalDateTime.now().plusDays(5);

        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        Doctor doctor3 = new Doctor();

        doctor1.setId(1L);
        doctor2.setId(2L);
        doctor3.setId(3L);

        LocalDateTime now = LocalDateTime.now();
        Leave leave1 = new Leave(1L, now.plusDays(1), now.plusDays(6), doctor1);
        Leave leave2 = new Leave(2L, now.plusDays(6), now.plusDays(7), doctor2);
        Leave leave3 = new Leave(3L, now.plusDays(4), now.plusDays(8), doctor3);

        doctor1.addLeave(leave1);
        doctor2.addLeave(leave2);
        doctor3.addLeave(leave3);


        Page<Doctor> doctors = new PageImpl<>(List.of(doctor1, doctor2, doctor3));
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(repository.findAll(pageable)).thenReturn(doctors);

        // Act
        var response = underTest.readAllAvailableByDate(date, pageable);

        // Assert
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(DoctorDTO.class, response.getBody().getContent().get(0).getClass());
        assertEquals(2, response.getBody().getContent().get(0).id());
    }

    @Test
    void shouldDeleteDoctorById() {
        // Arrange
        Long id = 1L;
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        when(repository.findById(id)).thenReturn(Optional.of(doctor1));

        // Act
        var response = underTest.deleteDoctor(id);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionIfDoctorToDeleteNotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.deleteDoctor(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Doctor with id = " + id) ;
        verify(repository, never()).deleteById(id);
    }

    @Test
    void shouldFindAllDoctors() {
        // Arrange
        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        doctor1.setId(1L);
        doctor2.setId(2L);
        Page<Doctor> doctors = new PageImpl<>(List.of(doctor1, doctor2));
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(repository.findAll(pageable)).thenReturn(doctors);

        // Act
        var response = underTest.readAll(pageable);

        // Assert
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(DoctorDTO.class, response.getBody().getContent().get(0).getClass());
        assertEquals(2, response.getBody().getContent().get(1).id());

    }

    @Test
    void shouldThrowEmptyPageException() {
        // Arrange
        Page<Doctor> doctors = new PageImpl<>(List.of());
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(repository.findAll(pageable)).thenReturn(doctors);

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.readAll(pageable))
                .isInstanceOf(EmptyPageException.class)
                .hasMessage("Page is empty");
    }

    @Test
    void shouldReadAllTypesOfVisits() {
        // Arrange
        Long doctorId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        TypeOfVisit type1 = new TypeOfVisit(1L, "Consultation", BigDecimal.TEN, "USD", 30, true, new Doctor());
        TypeOfVisit type2 = new TypeOfVisit(2L, "Surgery", BigDecimal.valueOf(100), "USD", 60, true, new Doctor());
        Page<TypeOfVisit> types = new PageImpl<>(List.of(type1, type2));

        when(typeOfVisitRepository.findAllByDoctorIdAndIsActive(doctorId, true, pageable)).thenReturn(types);

        // Act
        var response = underTest.readAllTypesOfVisits(doctorId, pageable);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(DoctorTypeOfVisitDTO.class, response.getBody().getContent().get(0).getClass());
    }

    @Test
    void shouldRemoveTypeOfVisit() {
        // Arrange
        Long doctorId = 1L;
        Long typeOfVisitId = 1L;
        Doctor doctor = new Doctor();
        TypeOfVisit type = new TypeOfVisit(typeOfVisitId, "Consultation", BigDecimal.TEN, "USD", 30, true, doctor);
        doctor.addTypeOfVisit(type);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.removeTypeOfVisit(doctorId, typeOfVisitId);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertFalse(type.isActive());
        verify(repository).save(doctor);
    }

    @Test
    void shouldAddTypeOfVisit() {
        // Arrange
        Long doctorId = 1L;
        DoctorTypeOfVisitDTO dto = new DoctorTypeOfVisitDTO(1L, "Consultation", BigDecimal.TEN, "USD", 30, true);
        Doctor doctor = new Doctor();

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.addTypeOfVisit(doctorId, dto);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertEquals(1, doctor.getTypesOfVisits().size());
        verify(repository).save(doctor);
    }

    @Test
    void shouldThrowExceptionWhenAddingTypeOfVisitExceedsLimit() {
        // Arrange
        Long doctorId = 1L;
        DoctorTypeOfVisitDTO dto = new DoctorTypeOfVisitDTO(1L, "Consultation", BigDecimal.TEN, "USD", 30, true);
        Doctor doctor = new Doctor();
        for (int i = 0; i < 30; i++) {
            doctor.addTypeOfVisit(new TypeOfVisit());
        }

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act & Assert
        assertThatThrownBy(() -> underTest.addTypeOfVisit(doctorId, dto))
                .isInstanceOf(WrongTypeOfVisitException.class)
                .hasMessageContaining("has reached the maximum number of types");
    }

    @Test
    void shouldUpdateSchedule() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        Schedule schedule = new Schedule(LocalTime.of(8, 0), LocalTime.of(16, 0));
        doctor.setSchedule(schedule);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        var response = underTest.updateSchedule(doctorId, schedule);

        // Assert
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(repository).save(doctor);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingScheduleWithInvalidTimes() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        Schedule schedule = new Schedule(LocalTime.of(16, 0), LocalTime.of(8, 0));
        doctor.setSchedule(schedule);

        when(repository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act & Assert
        assertThatThrownBy(() -> underTest.updateSchedule(doctorId, schedule))
                .isInstanceOf(WrongScheduleException.class)
                .hasMessageContaining("start time can not be after end time");
    }

    @Test
    void shouldSearchDoctorsBySpecialization() {
        // Arrange
        String word = "John";
        String specialization = "Cardiology";
        Pageable pageable = PageRequest.of(0, 10);
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Amy");
        Doctor doctor2 = new Doctor();
        doctor2.setFirstName("Bob");
        Page<Doctor> doctors = new PageImpl<>(List.of(doctor1, doctor2));

        when(repository.searchWithSpecialization(word, specialization)).thenReturn(List.of(doctor1, doctor2));

        // Act
        var response = underTest.searchDoctors(word, specialization, pageable);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(DoctorDTO.class, response.getBody().getContent().get(0).getClass());
    }

    @Test
    void shouldSearchDoctorsWithoutSpecialization() {
        // Arrange
        String word = "John";
        Pageable pageable = PageRequest.of(0, 10);
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Amy");
        Doctor doctor2 = new Doctor();
        doctor2.setFirstName("Bob");
        Page<Doctor> doctors = new PageImpl<>(List.of(doctor1, doctor2));

        when(repository.search(word)).thenReturn(List.of(doctor1, doctor2));

        // Act
        var response = underTest.searchDoctors(word, null, pageable);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(DoctorDTO.class, response.getBody().getContent().get(0).getClass());
    }
}