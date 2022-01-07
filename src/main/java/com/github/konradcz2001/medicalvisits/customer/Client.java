package com.github.konradcz2001.medicalvisits.customer;

import com.github.konradcz2001.medicalvisits.visit.Visit;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    @OneToMany
    private Set<Visit> visits;

    public long getId() {
        return id;
    }

    void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    void setSurname(final String surname) {
        this.surname = surname;
    }
}
