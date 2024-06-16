package com.github.konradcz2001.medicalappointments.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//TODO organize DoctorRepository

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * User connected
     */
    Optional<Doctor> findByEmail(String email);
    Boolean existsByEmail(String email);
    Page<Doctor> findAllByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Doctor> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Page<Doctor> findAllByEmailContainingIgnoreCase(String email, Pageable pageable);

    /**
     * Address connected
     */
    Page<Doctor> findAllByAddress_CountryContainingIgnoreCase(String country, Pageable pageable);
    Page<Doctor> findAllByAddress_StateContainingIgnoreCase(String state, Pageable pageable);
    Page<Doctor> findAllByAddress_CityContainingIgnoreCase(String city, Pageable pageable);
    Page<Doctor> findAllByAddress_StreetContainingIgnoreCase(String street, Pageable pageable);
    Page<Doctor> findAllByAddress_HouseNumberContainingIgnoreCase(String number, Pageable pageable);
    Page<Doctor> findAllByAddress_ZipCodeContainingIgnoreCase(String zipCode, Pageable pageable);

    /**
     * Leaves connected
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
            nativeQuery = true)
    Page<Doctor> findAllByAnyLeaveIsBetween(Timestamp after, Timestamp before, Pageable pageable);

    /**
     * Other
     */
    Page<Doctor> findAllByVerifiedIs(boolean isVerified, Pageable pageable);

    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, password, role, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "JOIN doctor_specialization ON doctor_id = doctors.id " +
            "JOIN specializations ON specialization_id = specializations.id " +
            "WHERE UPPER(specialization) LIKE '%' || UPPER(?1) || '%' " +
            "ORDER BY first_name ",
            nativeQuery = true)
    Page<Doctor> findAllByAnySpecializationContainingIgnoreCase(String specialization, Pageable pageable);

    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, password, role, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "LEFT JOIN doctor_specialization ON doctor_id = doctors.id " +
            "LEFT JOIN specializations ON specialization_id = specializations.id " +
            "WHERE (LOWER(first_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(last_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(country) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(state) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(city) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(zip_code) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(street) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(house_number) LIKE '%' || LOWER(?1) || '%') ",
            nativeQuery = true)
    List<Doctor> search(String word);

    @Query(value = " SELECT DISTINCT doctors.id, first_name, last_name, email, password, role, country, state, city, " +
            "street, house_number, zip_code, avatar, is_verified, profile_description FROM doctors " +
            "LEFT JOIN doctor_specialization ON doctor_id = doctors.id " +
            "LEFT JOIN specializations ON specialization_id = specializations.id " +
            "WHERE UPPER(specialization) LIKE UPPER(?2) " +
            "AND (LOWER(first_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(last_name) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(country) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(state) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(city) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(zip_code) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(street) LIKE '%' || LOWER(?1) || '%' " +
            "OR LOWER(house_number) LIKE '%' || LOWER(?1) || '%') ",
            //countQuery = " SELECT count(*) FROM doctors ",
            nativeQuery = true)
    List<Doctor> searchWithSpecialization(String word, String specialization);

}
