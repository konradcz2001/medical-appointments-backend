package com.github.konradcz2001.medicalappointments.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
