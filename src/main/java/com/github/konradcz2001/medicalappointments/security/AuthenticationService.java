package com.github.konradcz2001.medicalappointments.security;

import com.github.konradcz2001.medicalappointments.User;
import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import com.github.konradcz2001.medicalappointments.exception.WrongRoleException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ClientRepository clientRepository;
    private final DoctorRepository doctorRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService, ClientRepository clientRepository, DoctorRepository doctorRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
        this.doctorRepository = doctorRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request){
        if(request.getRole() == Role.CLIENT){
            Client client = (Client)createUser(request, new Client());
            clientRepository.save(client);

            String token = jwtService.generateToken(client);
            return new AuthenticationResponse(token);
        }
        else if(request.getRole() == Role.DOCTOR){
            Doctor doctor = (Doctor)createUser(request, new Doctor());
            doctorRepository.save(doctor);

            String token = jwtService.generateToken(doctor);
            return new AuthenticationResponse(token);
        }
        throw new WrongRoleException();
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));



        Optional<Client> client = clientRepository.findByEmail(request.getUsername());
        if(client.isPresent()){
            String token = jwtService.generateToken(client.get());
            return new AuthenticationResponse(token);
        }

        Optional<Doctor> doctor = doctorRepository.findByEmail(request.getUsername());
        if(doctor.isPresent()){
            String token = jwtService.generateToken(doctor.get());
            return new AuthenticationResponse(token);
        }

        throw new WrongRoleException();//TODO other exception
    }

    private User createUser(User request, User user) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return user;
    }

}
