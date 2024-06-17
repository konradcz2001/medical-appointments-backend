package com.github.konradcz2001.medicalappointments.common;

import com.github.konradcz2001.medicalappointments.exception.exceptions.EmptyPageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilsTest {

    @Mock
    Supplier<Page<Object>> suppliedResources;
    @Mock
    DTOMapper<Object, Object> dtoMapper;

    @Test
    void shouldThrowEmptyPageException() {
        // Arrange
        when(suppliedResources.get()).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        // Assert
        assertThatThrownBy(() -> returnResponse(suppliedResources, dtoMapper))
                .isInstanceOf(EmptyPageException.class)
                .hasMessage("Page is empty");
        verify(suppliedResources).get();
        verify(dtoMapper, never()).mapToDTO(any());
    }


    @Test
    void shouldReturnResponseEntityWithContent() {
        // Arrange
        List<Object> content = new ArrayList<>();
        content.add(1);
        when(suppliedResources.get()).thenReturn(new PageImpl<>(content));
        when(dtoMapper.mapToDTO(any())).thenReturn(content.get(0));

        // Act
        var response = returnResponse(suppliedResources, dtoMapper);

        // Assert
        verify(dtoMapper).mapToDTO(isA(Object.class));
        verify(suppliedResources).get();
        assertEquals(1, response.getBody().toList().size());
        assertEquals(content.get(0), response.getBody().toList().get(0));
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.hasBody());
    }
}
