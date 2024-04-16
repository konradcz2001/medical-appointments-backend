package com.github.konradcz2001.medicalappointments.common;

public interface DTOMapper <T, S> {
    T apply(S sourceClass);
}
