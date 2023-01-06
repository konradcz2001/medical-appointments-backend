package com.github.konradcz2001.medicalappointments.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


interface DoctorRepository extends JpaRepository<Doctor, Long> {
    /*
        UserData
     */
    Page<Doctor> findAllByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Doctor> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Page<Doctor> findFirstByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Doctor> findFirstByPhoneNumberContainingIgnoreCase(String phoneNumber, Pageable pageable);
    /*
        Address
     */
    Page<Doctor> findAllByAddress_CountryContainingIgnoreCase(String country, Pageable pageable);
    Page<Doctor> findAllByAddress_StateContainingIgnoreCase(String state, Pageable pageable);
    Page<Doctor> findAllByAddress_CityContainingIgnoreCase(String city, Pageable pageable);
    Page<Doctor> findAllByAddress_StreetContainingIgnoreCase(String street, Pageable pageable);
    Page<Doctor> findAllByAddress_NumberContainingIgnoreCase(String number, Pageable pageable);
    Page<Doctor> findAllByAddress_ZipCodeContainingIgnoreCase(String zipCode, Pageable pageable);
    /*
        Leaves
     */
    @Query(value = " SELECT doctors.id, first_name, last_name, email, phone_number, country, state, city, " +
            "street, number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN leaves ON doctor_id = doctors.id " +
            "WHERE since_when AFTER ?1 AND till_when BEFORE ?2 " +
            "ORDER BY doctors.id ",
            countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    Page<Doctor> findAllByAnyLeaveIsBetween(LocalDateTime after, LocalDateTime before, Pageable pageable);
    /*
        Other
     */
    @Query(value = " SELECT doctors.id, first_name, last_name, email, phone_number, country, state, city, " +
            "street, number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN doctor_specialization ON doctor_id = doctors.id " +
            "JOIN specializations ON specialization_id = specializations.id " +
            "WHERE LOWER(specialization) LIKE '%' || LOWER(?1) || '%' " +
            "ORDER BY doctors.id ",
            countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    Page<Doctor> findAllByAnySpecializationContainingIgnoreCase(String specialization, Pageable pageable);
    Page<Doctor> findAllByIsVerified(boolean isVerified, Pageable pageable);

    @Query(value = " SELECT * FROM doctors " +
            "WHERE LOWER(first_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(last_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(country) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(city) LIKE '%' || LOWER(?1) || '%' " +
            "ORDER BY id ",
            countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    Page<Doctor> search(String word, Pageable pageable);
}
