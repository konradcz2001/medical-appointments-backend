package com.github.konradcz2001.medicalappointments.leave;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTO;
import com.github.konradcz2001.medicalappointments.leave.DTO.LeaveDTOMapper;
import org.junit.jupiter.api.Assertions;
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
class LeaveServiceTest {
    @Mock
    private LeaveRepository repository;
    @Spy
    private LeaveDTOMapper dtoMapper;
    @InjectMocks
    private LeaveService underTest;



    @Test
    void shouldFindLeaveById() {
        // Arrange
        Leave leave1 = new Leave();
        Leave leave2 = new Leave();
        leave1.setId(1L);
        leave2.setId(2L);
        leave1.setDoctor(new Doctor());
        leave2.setDoctor(new Doctor());

        when(repository.findById(2L)).thenReturn(Optional.of(leave2));

        // Act
        var response = underTest.readById(2L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(LeaveDTO.class, response.getBody().getClass());
        Assertions.assertEquals(2, response.getBody().id());
    }

}