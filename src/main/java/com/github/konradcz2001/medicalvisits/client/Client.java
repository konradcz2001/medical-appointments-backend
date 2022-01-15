package com.github.konradcz2001.medicalvisits.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter(AccessLevel.PACKAGE)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;


}
