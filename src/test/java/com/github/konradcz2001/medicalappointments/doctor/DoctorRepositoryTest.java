package com.github.konradcz2001.medicalappointments.doctor;
import com.github.konradcz2001.medicalappointments.Address;
import com.github.konradcz2001.medicalappointments.doctor.leave.Leave;
import com.github.konradcz2001.medicalappointments.doctor.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.doctor.specialization.Specialization;
import com.github.konradcz2001.medicalappointments.doctor.specialization.SpecializationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    DoctorRepository underTest;
    @Autowired
    SpecializationRepository specializationRepo;
    @Autowired
    LeaveRepository leaveRepo;

    @BeforeEach
    void setUp() {
        prepareData();
    }


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        specializationRepo.deleteAll();
        leaveRepo.deleteAll();
    }

    @Test
    void shouldFindAllDoctorsByGivenFirstName() {
        // Given
        String firstName = "John";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByFirstNameContainingIgnoreCase(firstName, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getFirstName().toLowerCase().contains(firstName.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenLastName() {
        // Given
        String lastName = "White";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByLastNameContainingIgnoreCase(lastName, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getLastName().toLowerCase().contains(lastName.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindFirstDoctorByGivenEmail() {
        // Given
        String email = "@gmail.com";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByEmailContainingIgnoreCase(email, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getEmail().toLowerCase().contains(email.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindFirstDoctorByGivenPhoneNumber() {
        // Given
        String phoneNumber = "111";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByPhoneNumberContainingIgnoreCase(phoneNumber, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getPhoneNumber().toLowerCase().contains(phoneNumber.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenCountry() {
        // Given
        String country = "Poland";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_CountryContainingIgnoreCase(country, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getCountry().toLowerCase().contains(country.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenState() {
        // Given
        String state = "California";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_StateContainingIgnoreCase(state, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getState().toLowerCase().contains(state.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenCity() {
        // Given
        String city = "New";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_CityContainingIgnoreCase(city, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getCity().toLowerCase().contains(city.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenStreet() {
        // Given
        String street = "Wyzwolenia";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_StreetContainingIgnoreCase(street, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getStreet().toLowerCase().contains(street.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenHouseNumber() {
        // Given
        String number = "45";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_NumberContainingIgnoreCase(number, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getNumber().toLowerCase().contains(number.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenZipCode() {
        // Given
        String zipCode = "43";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAddress_ZipCodeContainingIgnoreCase(zipCode, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getAddress().getZipCode().toLowerCase().contains(zipCode.toLowerCase()))).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByGivenSpecialization() {
        // Given
        String spec = "atolog";
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAnySpecializationContainingIgnoreCase(spec, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream().allMatch(doctor -> {
            return doctor.getSpecializations().stream().anyMatch(specialization -> {
                    return specialization.getSpecialization().toLowerCase().contains(spec.toLowerCase());
            });
        })).isTrue();
    }

    @Test
    void shouldFindAllDoctorsByVerified() {
        // Given
        boolean isVerified = true;
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByIsVerified(isVerified, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.isVerified() == isVerified)).isTrue();
    }



    @Test
    void shouldFindAllDoctorsByLeaveIsBetweenGivenDates() {
        // Given
        LocalDateTime after = LocalDateTime.of(2001, Month.JANUARY, 1, 0, 0);
        LocalDateTime before = LocalDateTime.of(2007, Month.JANUARY, 1, 0, 0);
        Timestamp a = Timestamp.valueOf(after);
        Timestamp b = Timestamp.valueOf(before);
        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Doctor> doctors = underTest.findAllByAnyLeaveIsBetween(a, b, pageable);

        // Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream().allMatch(doctor -> {
                    return doctor.getLeaves().stream().anyMatch(leave -> {
                        return (leave.getSinceWhen().isAfter(after) && leave.getTillWhen().isBefore(before));
                    });
                })).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "pO", "hn", "Us" })
    void shouldFindDoctorsWhere_FirstNameOrLastNameOrCountryOrCity_ContainingGivenWord(String searchTerm) {
        //Given
        Pageable pageable= PageRequest.of(0, 5);

        //When
        Page<Doctor> doctors = underTest.search(searchTerm, pageable);

        //Then
        assertThat(doctors).isNotNull();
        assertThat(doctors.getTotalElements()).isGreaterThan(0);
        assertThat(doctors.getContent().stream()
                .allMatch(doctor -> doctor.getFirstName().toLowerCase().contains(searchTerm.toLowerCase())
                        || doctor.getLastName().toLowerCase().contains(searchTerm.toLowerCase())
                        || doctor.getAddress().getCountry().toLowerCase().contains(searchTerm.toLowerCase())
                        || doctor.getAddress().getCity().toLowerCase().contains(searchTerm.toLowerCase()))).isTrue();
    }

    private void prepareData() {

        Doctor d1 = new Doctor("John", "White", "johnwhite@gmail.com", "111",
                new Address("Poland", "śląskie", "Pszczyna", "Wyzwolenia", "45/2", "43-200"),
                true, null, null);
        Doctor d2 = new Doctor("John", "Williams", "johnwilliams@gmail.com", "222",
                new Address("USA", "California", "Los Angeles", "First Street", "45a", "90001"),
                true, null, null);
        Doctor d3 = new Doctor("John", "Smith", "johnsmith@gmail.com", "2222",
                new Address("Poland", "mazowieckie", "Warszawa", "Powstańców", "85", "00-001"),
                false, null, null);
        Doctor d4 = new Doctor("Ariana", "White", "arianawhite@gmail.com", "1111",
                new Address("USA", "New York", "New York", "Academy Street", "32", "10001"),
                true, null, null);


        Specialization s1 = new Specialization("Stomatology");
        Specialization s2 = new Specialization("Cardiology");
        Specialization s3 = new Specialization("Cosmetic Surgery");

        specializationRepo.save(s1);
        specializationRepo.save(s2);
        specializationRepo.save(s3);

        d1.addSpecialization(s1);
        d1.addSpecialization(s2);
        d2.addSpecialization(s1);
        d3.addSpecialization(s2);
        d3.addSpecialization(s3);

        underTest.save(d1);
        underTest.save(d2);
        underTest.save(d3);
        underTest.save(d4);

        Leave l2_1 = new Leave(LocalDateTime.of(2000, Month.JANUARY, 1,0,0,0),
                LocalDateTime.of(2001, Month.JANUARY, 1,0,0,0));
        Leave l2_2 = new Leave(LocalDateTime.of(2002, Month.JANUARY, 1,0,0,0),
                LocalDateTime.of(2003, Month.JANUARY, 1,0,0,0));

        Leave l3 = new Leave(LocalDateTime.of(2004, Month.JANUARY, 1,0,0,0),
                LocalDateTime.of(2005, Month.JANUARY, 1,0,0,0));

        Leave l4 = new Leave(LocalDateTime.of(2000, Month.JANUARY, 1,0,0,0),
                LocalDateTime.of(2007, Month.JANUARY, 1,0,0,0));

        l2_1.setDoctor(d2);
        l2_2.setDoctor(d2);
        l3.setDoctor(d3);
        l4.setDoctor(d4);

        leaveRepo.save(l2_1);
        leaveRepo.save(l2_2);
        leaveRepo.save(l3);
        leaveRepo.save(l4);
    }
}