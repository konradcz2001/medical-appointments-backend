package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorFacadeTest {
    @Mock
    private DoctorRepository repository;
    @Mock
    private LeaveRepository leaveRepo;
    @Mock
    private SpecializationRepository specializationRepo;
    @InjectMocks
    private DoctorFacade underTest;


    @Test
    void shouldSaveLeave_DoctorLeaveIsBetweenGivenLeave() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(2000,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(2100,Month.JANUARY,1,0,0),
                LocalDateTime.of(2200,Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        underTest.addLeave(leave, doctor);

        //Then
        verify(leaveRepo).save(doctorLeave);
        verify(leaveRepo, never()).save(leave);
        assertEquals(leave.getSinceWhen(), doctorLeave.getSinceWhen());
        assertEquals(leave.getTillWhen(), doctorLeave.getTillWhen());
    }

    @Test
    void shouldThrowExceptionThatThereIsSuchALeave_BeginningOfDoctorLeaveIsInPast_BeginningOfGivenLeaveIsBeforeBeginningOfDoctorLeaveAndEndIsInside() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(1900,Month.JANUARY,1,0,0),
                LocalDateTime.of(2300,Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(2000,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        //Then
        assertThatThrownBy(() -> underTest.addLeave(leave, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Leave for the given period of time already exists");
    }

    @Test
    void shouldSaveLeave_BeginningOfGivenLeaveIsBeforeBeginningOfDoctorLeaveAndEndIsInside() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(2100,Month.JANUARY,1,0,0),
                LocalDateTime.of(2400,Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(2200,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        underTest.addLeave(leave, doctor);

        //Then
        assertEquals(leave.getSinceWhen(), doctorLeave.getSinceWhen());
        verify(leaveRepo).save(doctorLeave);
        verify(leaveRepo, never()).save(leave);
    }

    @Test
    void shouldSaveLeave_BeginningOfDoctorLeaveIsBeforeBeginningOfGivenLeaveAndEndIsInside() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(2200,Month.JANUARY,1,0,0),
                LocalDateTime.of(2400,Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(2100,Month.JANUARY,1,0,0),
                LocalDateTime.of(2300,Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        underTest.addLeave(leave, doctor);

        //Then
        assertEquals(leave.getTillWhen(), doctorLeave.getTillWhen());
        verify(leaveRepo).save(doctorLeave);
        verify(leaveRepo, never()).save(leave);
    }

    @ParameterizedTest
    @CsvSource({"2100, 2200, 2300, 2400", "2300, 2400, 2100, 2200"})
    void shouldSaveLeave_DoctorLeaveIsBeforeOrAfterGivenLeave(int leaveSince, int leaveTill, int doctorLeaveSince, int doctorLeaveTill) {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(leaveSince, Month.JANUARY,1,0,0),
                LocalDateTime.of(leaveTill, Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(doctorLeaveSince, Month.JANUARY,1,0,0),
                LocalDateTime.of(doctorLeaveTill, Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        underTest.addLeave(leave, doctor);

        //Then
        verify(leaveRepo).save(leave);
        verify(leaveRepo, never()).save(doctorLeave);
    }

    @Test
    void shouldThrowExceptionThatThereIsSuchALeave_GivenLeaveIsBetweenDoctorLeave() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(2100,Month.JANUARY,1,0,0),
                LocalDateTime.of(2300,Month.JANUARY,1,0,0));
        Leave doctorLeave = new Leave(LocalDateTime.of(2000,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        doctorLeave.setDoctor(doctor);

        //When
        //Then
        assertThatThrownBy(() -> underTest.addLeave(leave, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Leave for the given period of time already exists");
    }
    @Test
    void shouldSaveLeave_doctorHasNoLeaves() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(2000,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        //When
        underTest.addLeave(leave, doctor);

        //Then
        verify(leaveRepo).save(leave);
    }

    @Test
    void shouldThrowExceptionThatBeginningOfLeaveCannotBeBeforeEnd() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(3000,Month.JANUARY,1,0,0),
                LocalDateTime.of(2500,Month.JANUARY,1,0,0));
        //When
        //Then
        assertThatThrownBy(() -> underTest.addLeave(leave, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The beginning of the leave cannot be later than the end");
    }

    @Test
    void shouldThrowExceptionThatLeaveIsOver() {
        //Given
        Doctor doctor = new Doctor();
        Leave leave = new Leave(LocalDateTime.of(1,Month.JANUARY,1,0,0),
                LocalDateTime.of(1,Month.JANUARY,1,0,0));
        //When
        //Then
        assertThatThrownBy(() -> underTest.addLeave(leave, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The leave is over");
    }

    @Test
    void shouldRemoveLeave() {
        //Given
        Long id = 1L;
        Doctor doctor = spy(new Doctor());
        Leave leave1 = new Leave();
        Leave leave2 = new Leave();
        leave1.setId(1L);
        leave2.setId(2L);
        doctor.addLeave(leave1);
        when(leaveRepo.findById(anyLong())).thenReturn(Optional.of(leave1));

        //When
        underTest.removeLeave(id, doctor);

        //Then
        verify(doctor).removeLeave(leave1);
        verify(leaveRepo).delete(leave1);
    }

    @Test
    void shouldThrowExceptionThatThereIsNoLeaveWithSuchId() {
        //Given
        Long id = 3L;
        Doctor doctor = new Doctor();
        Leave leave1 = new Leave();
        Leave leave2 = new Leave();
        leave1.setId(1L);
        leave2.setId(2L);
        doctor.addLeave(leave1);
        when(leaveRepo.findById(anyLong())).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(() -> underTest.removeLeave(id, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Leave with id = " + id + " does not exist");
    }
    @Test
    void shouldThrowExceptionThatDoctorHasNoLeaveWithGivenId() {
        //Given
        Long id = 2L;
        Doctor doctor = new Doctor();
        Leave leave1 = new Leave();
        Leave leave2 = new Leave();
        leave1.setId(1L);
        leave2.setId(2L);
        doctor.addLeave(leave1);
        when(leaveRepo.findById(anyLong())).thenReturn(Optional.of(leave2));

        //When
        //Then
        assertThatThrownBy(() -> underTest.removeLeave(id, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Doctor has no leave with id = " + id);
    }

    @Test
    void shouldThrowExceptionThatThereIsNoSpecializationWithGivenId_removeSpecialization() {
        //Given
        Integer id = 2;
        Doctor doctor = new Doctor();
        when(specializationRepo.findById(anyInt())).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(() -> underTest.removeSpecialization(id, doctor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Specialization with id = " + id + " does not exist");
    }

    @Test
    void shouldRemoveSpecializationWithGivenId_removeSpecialization() {
        //Given
        Integer id = 2;
        Doctor doctor = spy(new Doctor());
        Specialization spec = new Specialization();
        spec.setId(id);
        when(specializationRepo.findById(anyInt())).thenReturn(Optional.of(spec));

        //When
        underTest.removeSpecialization(id, doctor);

        //Then
        verify(doctor).removeSpecialization(spec);
        verify(repository).save(doctor);
    }

    @Test
    void shouldReadAllDoctorsAvailableByDate(){
        //given
        LocalDateTime date = LocalDateTime.of(5, Month.JANUARY,1,0,0);
        Doctor d1 = new Doctor();
        Doctor d2 = new Doctor();
        Doctor d3 = new Doctor();
        d1.addLeave(new Leave(LocalDateTime.of(1, Month.JANUARY,1,0,0),
                LocalDateTime.of(10, Month.JANUARY,1,0,0)));
        d2.addLeave(new Leave(LocalDateTime.of(1, Month.JANUARY,1,0,0),
                LocalDateTime.of(5, Month.JANUARY,1,0,0)));
        d3.addLeave(new Leave(LocalDateTime.of(1, Month.JANUARY,1,0,0),
                LocalDateTime.of(3, Month.JANUARY,1,0,0)));

        when(repository.findAll()).thenReturn(Arrays.asList(d1, d2, d3));

        //when
        List<Doctor> doctors = underTest.readAllAvailableByDate(date);

        //then
        assertEquals(1, doctors.size());
    }

    @Test
    void shouldReturnFalse_isAvailableByDate(){
        //given
        LocalDateTime date = LocalDateTime.of(1, Month.JANUARY,1,0,0);
        Doctor doctor = spy(new Doctor());
        Set<Leave> leaves = new HashSet<>();
        leaves.add(new Leave(
                LocalDateTime.of(1, Month.JANUARY,1,0,0),
                LocalDateTime.of(2, Month.JANUARY,1,0,0)));

        when(doctor.getLeaves()).thenReturn(leaves);

        //when
        //then
        assertFalse(underTest.isAvailableByDate(date, doctor));
    }

    @Test
    void shouldReturnTrue_isAvailableByDate(){
        //given
        LocalDateTime date = LocalDateTime.of(3, Month.JANUARY,1,0,0);
        Doctor doctor = spy(new Doctor());
        Set<Leave> leaves = new HashSet<>();
        leaves.add(new Leave(
                LocalDateTime.of(1, Month.JANUARY,1,0,0),
                LocalDateTime.of(2, Month.JANUARY,1,0,0)));

        when(doctor.getLeaves()).thenReturn(leaves);

        //when
        //then
        assertTrue(underTest.isAvailableByDate(date, doctor));
    }

    @Test
    void shouldAddNewDoctor() {
        //given
        Doctor doctor = spy(new Doctor());

        //when
        underTest.addDoctor(doctor);

        //then
        verify(repository).save(doctor);
        verify(doctor).setId(null);
        verify(doctor).setSpecializations(new HashSet<>());
        verify(doctor).setLeaves(new HashSet<>());
    }

    @Test
    void shouldThrowExceptionIfSpecializationNotExist_addSpecializations() {
        //given
        Doctor doctor = new Doctor();
        Set<Integer> ids = Stream.of(1, 2, 3).collect(Collectors.toSet());
        when(specializationRepo.findById(anyInt())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.addSpecializations(doctor, ids))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Specialization with id = ");
        verify(specializationRepo, times(1)).findById(anyInt());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldSaveDoctor_addSpecializations() {
        //given
        Doctor doctor = spy(new Doctor());
        Set<Integer> ids = Stream.of(1, 2, 3).collect(Collectors.toSet());
        when(specializationRepo.findById(anyInt())).thenReturn(Optional.of(new Specialization()));

        //when
        underTest.addSpecializations(doctor, ids);

        //then
        verify(specializationRepo, times(3)).findById(anyInt());
        verify(doctor, times(3)).addSpecialization(any());
        verify(repository).save(doctor);
    }
}