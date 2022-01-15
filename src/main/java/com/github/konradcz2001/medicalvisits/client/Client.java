package com.github.konradcz2001.medicalvisits.client;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;

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
