package com.github.konradcz2001.medicalvisits.visit;

import com.github.konradcz2001.medicalvisits.client.Client;
import com.github.konradcz2001.medicalvisits.doctor.Doctor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime deadline;
    private String type;
    private String notes;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Client client;


    public long getId() {
        return id;
    }

    void setId(final long id) {
        this.id = id;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    void setType(final String typeOfVisit) {
        this.type = typeOfVisit;
    }

    public String getNotes() {
        return notes;
    }

    void setNotes(final String notes) {
        this.notes = notes;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    void setDoctor(final Doctor doctor) {
        this.doctor = doctor;
    }

    public Client getClient() {
        return client;
    }

    void setClient(final Client client) {
        this.client = client;
    }
}
