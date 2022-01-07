package com.github.konradcz2001.medicalvisits.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByName(String name);
    List<Customer> findAllBySurname(String surname);
}
