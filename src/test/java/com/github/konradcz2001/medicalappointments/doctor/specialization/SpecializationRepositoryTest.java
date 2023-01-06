package com.github.konradcz2001.medicalappointments.doctor.specialization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SpecializationRepositoryTest {
    @Autowired
    private SpecializationRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindFirstBySpecialization() {
        //given
        String spec = "spec";
        underTest.save(new Specialization("a"));
        underTest.save(new Specialization(spec));
        //when
        var expected = underTest.findFirstBySpecialization(spec);
        //then
        assertTrue(expected.isPresent());
        assertEquals(expected.get().getSpecialization(), spec);
    }

    @Test
    void shouldNotFindBySpecialization() {
        //given
        String spec = "spec";
        underTest.save(new Specialization("a"));
        underTest.save(new Specialization("b"));
        //when
        var expected = underTest.findFirstBySpecialization(spec);
        //then
        assertFalse(expected.isPresent());
    }

    @Test
    void shouldCheckIfExistsBySpecializationProperty() {
        //given
        String spec = "spec";
        underTest.save(new Specialization(spec));
        //when
        boolean expected = underTest.existsBySpecialization(spec);
        //then
        assertTrue(expected);
    }

    @Test
    void shouldCheckIfDoesNotExistsBySpecializationProperty() {
        //given
        String spec = "spec";
        //when
        boolean expected = underTest.existsBySpecialization(spec);
        //then
        assertFalse(expected);
        System.out.println(underTest.findAll().size());
    }
}