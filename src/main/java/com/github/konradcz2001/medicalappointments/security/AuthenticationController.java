package com.github.konradcz2001.medicalappointments.security;

import com.github.konradcz2001.medicalappointments.common.User;
import com.github.konradcz2001.medicalappointments.security.DTO.UserRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
    private final  AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Registers a new user based on the provided UserRegisterDTO.
     *
     * @param user The UserRegisterDTO object containing the user's information.
     * @return ResponseEntity with the AuthenticationResponse object containing the authentication token.
     */
    @Operation(summary = "Registers a new user.")
    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register( @RequestBody UserRegisterDTO user){
        return ResponseEntity.ok(authenticationService.register(user));
    }

    /**
     * Authenticates a user based on the provided User object.
     *
     * @param user The User object containing the user's information.
     * @return ResponseEntity with the AuthenticationResponse object containing the authentication token.
     */
    @Operation(summary = "Authenticates a user.")
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody User user){
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
