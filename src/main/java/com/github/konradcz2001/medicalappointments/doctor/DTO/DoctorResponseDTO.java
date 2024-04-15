package com.github.konradcz2001.medicalappointments.doctor.DTO;

import com.github.konradcz2001.medicalappointments.doctor.Address;
import com.github.konradcz2001.medicalappointments.security.Role;

public record DoctorResponseDTO(Long id, String firstName, String lastName, String email,
                                Role role, boolean isVerified, byte[] avatar, String profileDescription,
                                Address address) {
                                //Address
                                //String country, String state, String city, String street,
                                //String houseNumber, String zipCode) {
}
