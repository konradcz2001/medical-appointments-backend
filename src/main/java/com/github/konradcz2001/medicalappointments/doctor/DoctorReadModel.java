package com.github.konradcz2001.medicalappointments.doctor;

import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class DoctorReadModel {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String country;
    String state;
    String city;
    String street;
    String number;
    String zipCode;
    boolean isVerified;
    byte[] avatar;
    String profileDescription;
    Set<Specialization> specializations;
    Set<Leave> leaves;

    DoctorReadModel(Doctor doctor){
        id = doctor.getId();
        firstName = doctor.getFirstName();
        lastName = doctor.getLastName();
        email = doctor.getEmail();
        phoneNumber = doctor.getPhoneNumber();
        country = doctor.getAddress().getCountry();
        state = doctor.getAddress().getState();
        city = doctor.getAddress().getCity();
        street = doctor.getAddress().getStreet();
        number = doctor.getAddress().getNumber();
        zipCode = doctor.getAddress().getZipCode();
        isVerified = doctor.isVerified();
        avatar = doctor.getAvatar();
        profileDescription = doctor.getProfileDescription();
        specializations = doctor.getSpecializations();
        leaves = doctor.getLeaves();
    }

}
