package com.github.konradcz2001.medicalappointments.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

//TODO DoctorRepository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    /*
        User
     */
    Page<Doctor> findAllByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Doctor> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Page<Doctor> findAllByEmailContainingIgnoreCase(String email, Pageable pageable);
    /*
        Address
     */
    Page<Doctor> findAllByAddress_CountryContainingIgnoreCase(String country, Pageable pageable);
    Page<Doctor> findAllByAddress_StateContainingIgnoreCase(String state, Pageable pageable);
    Page<Doctor> findAllByAddress_CityContainingIgnoreCase(String city, Pageable pageable);
    Page<Doctor> findAllByAddress_StreetContainingIgnoreCase(String street, Pageable pageable);
    Page<Doctor> findAllByAddress_HouseNumberContainingIgnoreCase(String number, Pageable pageable);
    Page<Doctor> findAllByAddress_ZipCodeContainingIgnoreCase(String zipCode, Pageable pageable);
    /*
        Leaves
     */
    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN leaves ON doctor_id = doctors.id " +
            "WHERE start_date > ?1 OR end_date < ?1 " +
            "ORDER BY doctors.id ",
            nativeQuery = true)
    Page<Doctor> findAllAvailableByDate(LocalDateTime date, Pageable pageable);

    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, phone_number, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN leaves ON doctor_id = doctors.id " +
            "WHERE since_when > ?1 AND till_when < ?2 " +
            "ORDER BY doctors.id ",
           // countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    Page<Doctor> findAllByAnyLeaveIsBetween(Timestamp after, Timestamp before, Pageable pageable);
    /*
        Other
     */
    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, phone_number, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN doctor_specialization ON doctor_id = doctors.id " +
            "JOIN specializations ON specialization_id = specializations.id " +
            "WHERE LOWER(specialization) LIKE '%' || LOWER(?1) || '%' " +
            "ORDER BY doctors.id ",
           // countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    Page<Doctor> findAllByAnySpecializationContainingIgnoreCase(String specialization, Pageable pageable);
    Page<Doctor> findAllByVerifiedIs(boolean isVerified, Pageable pageable);

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
