package com.github.konradcz2001.medicalappointments.security;

import com.github.konradcz2001.medicalappointments.common.User;
import com.github.konradcz2001.medicalappointments.security.DTO.UserRegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthenticationController {
    private final  AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register( @RequestBody UserRegisterDTO user){
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody User user){
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
