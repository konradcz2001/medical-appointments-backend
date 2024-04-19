package com.github.konradcz2001.medicalappointments.common;

/**
 * This is an interface for a DTO mapper.
 * It provides methods to map an object to a DTO and vice versa.
 *
 * @param <T> the type of the DTO
 * @param <S> the type of the source object
 */
public interface DTOMapper <T, S> {
    T mapToDTO(S source);
    S mapFromDTO(T sourceDTO, S target);
}
