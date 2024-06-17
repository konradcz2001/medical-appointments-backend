package com.github.konradcz2001.medicalappointments.exception.exceptions;

import com.github.konradcz2001.medicalappointments.exception.MessageType;

/**
 * This code snippet represents a class named ResourceNotFoundException.
 * It is a custom exception class that extends the RuntimeException class, specifically designed to handle cases where a resource is not found.
 * The class has a constructor that takes two parameters: a MessageType object and a Long object.
 * The constructor calls a private static method named chooseMessageType to generate the exception message.
 * The chooseMessageType method concatenates the value of the MessageType object with the id parameter and returns the result.
 * The exception message is then passed to the super constructor of the RuntimeException class.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(MessageType type, Long id) {
        super(chooseMessageType(type, id));
    }

    private static String chooseMessageType(MessageType type, Long id){
        return type.getValue() + " with id = " + id + " not found";
    }
}
