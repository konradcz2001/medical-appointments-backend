package com.github.konradcz2001.medicalappointments.common;

public interface DTOMapper <T, S> {
    T mapToDTO(S source);
    S mapFromDTO(T sourceDTO, S target);
}
