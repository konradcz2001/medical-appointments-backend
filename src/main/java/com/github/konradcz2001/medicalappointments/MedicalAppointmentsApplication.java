package com.github.konradcz2001.medicalappointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@SpringBootApplication
public class MedicalAppointmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAppointmentsApplication.class, args);

    }

    public static <T> ResponseEntity<Page<T>> returnResponse(Supplier<Page<T>> supplier){
        Page<T> all = supplier.get();
        if(all.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(all);
    }

}
