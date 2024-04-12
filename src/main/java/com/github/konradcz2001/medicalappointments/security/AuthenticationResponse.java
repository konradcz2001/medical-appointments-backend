package com.github.konradcz2001.medicalappointments.security;

public class AuthenticationResponse {
    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
