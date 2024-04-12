package com.github.konradcz2001.medicalappointments.security;

import com.github.konradcz2001.medicalappointments.client.Client;
import com.github.konradcz2001.medicalappointments.client.ClientRepository;
import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import com.github.konradcz2001.medicalappointments.doctor.DoctorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;

    public UserDetailsServiceImp(DoctorRepository doctorRepository, ClientRepository clientRepository) {
        this.doctorRepository = doctorRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByEmail(username);
        if (client.isPresent())
            return client.get();
        Optional<Doctor> doctor = doctorRepository.findByEmail(username);
        if (doctor.isPresent())
            return doctor.get();

        throw new UsernameNotFoundException(username);
    }
}
