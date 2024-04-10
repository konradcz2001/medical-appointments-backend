package com.github.konradcz2001.medicalappointments.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(MessageType type, Long id) {
        super(chooseMessageType(type, id));
    }

    private static String chooseMessageType(MessageType type, Long id){
        return type.getValue() + " with id = " + id + " not found";
    }
}
