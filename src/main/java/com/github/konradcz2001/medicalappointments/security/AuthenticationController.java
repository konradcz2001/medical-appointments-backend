package com.github.konradcz2001.medicalappointments.security;

import com.github.konradcz2001.medicalappointments.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final  AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody User user){
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody User user){
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
