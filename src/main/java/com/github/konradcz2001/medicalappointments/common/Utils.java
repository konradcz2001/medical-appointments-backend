package com.github.konradcz2001.medicalappointments.common;

import com.github.konradcz2001.medicalappointments.exception.exceptions.EmptyPageException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

/**
 * This code snippet represents a method named "returnResponse" in the "Utils" class.
 * It takes two generic types, T and S, and returns a ResponseEntity object containing a Page of type S.
 * The method accepts a Supplier of Page of type T and a DTOMapper object as parameters.
 * It retrieves all the resources from the suppliedResources using the get() method and maps them to DTOs using the dtoMapper.
 * If the resulting Page is empty, it throws an EmptyPageException.
 * Otherwise, it returns a ResponseEntity object with the mapped resources.
 */
public class Utils {
    public static <T, S> ResponseEntity<Page<S>> returnResponse(Supplier<Page<T>> suppliedResources, DTOMapper<S, T> dtoMapper) {
        var all = suppliedResources.get()
                .map(dtoMapper::mapToDTO);

        if(all.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(all);
    }
}
