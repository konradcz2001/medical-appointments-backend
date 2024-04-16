package com.github.konradcz2001.medicalappointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalAppointmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAppointmentsApplication.class, args);

    }
// TODO maybe in the future you will need this

//    public static <T> ResponseEntity<Page<T>> returnResponse(Supplier<Page<T>> supplier){
//        Page<T> all = supplier.get();
//        if(all.isEmpty())
//            throw new EmptyPageException();
//        return ResponseEntity.ok(all);
//    }

}
