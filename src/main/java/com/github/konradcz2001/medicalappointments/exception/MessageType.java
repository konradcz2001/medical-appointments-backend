package com.github.konradcz2001.medicalappointments.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This code snippet represents an enum called MessageType. It is used to define different types of messages in the ResourceNotFoundException.
 * Each message type has a corresponding value associated with it.
 * The enum includes the following message types: SPECIALIZATION, LEAVE, DOCTOR, CLIENT, VISIT, and REVIEW.
 * The value of each message type is stored as a string.
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    SPECIALIZATION("Specialization"),
    LEAVE("Leave"),
    DOCTOR("Doctor"),
    CLIENT("Client"),
    VISIT("Visit"),
    REVIEW("Review");

    private final String value;

}
