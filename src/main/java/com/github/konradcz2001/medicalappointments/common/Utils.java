package com.github.konradcz2001.medicalappointments.common;

import com.github.konradcz2001.medicalappointments.exception.EmptyPageException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class Utils {
    public static <T, S> ResponseEntity<Page<S>> returnResponse(Supplier<Page<T>> suppliedResources, DTOMapper<S, T> dtoMapper) {
        var all = suppliedResources.get()
                .map(dtoMapper::apply);
        if(all.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(all);
    }
}
