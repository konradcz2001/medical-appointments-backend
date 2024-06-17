package com.github.konradcz2001.medicalappointments.leave;


import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


/**
 * This code snippet represents the "Leave" class.
 * <p>
 * The "Leave" class is an entity class that represents a leave taken by a doctor.
 * It is mapped to the "leaves" table in the database.
 * <p>
 * The class has the following attributes:
 * - id: The unique identifier for the leave.
 * - startDate: The start date of the leave.
 * - endDate: The end date of the leave.
 * - doctor: The doctor who took the leave.
 * <p>
 * The class provides getters and setters for all the attributes.
 * <p>
 * Note: The class uses Lombok annotations for generating getters, setters, and default constructor.
 * It also uses JPA annotations for mapping the class to the database table.
 */
@Entity
@Table(name = "leaves")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date must not be empty")
    LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date must not be empty")
    @FutureOrPresent(message = "End date must not be in the past")
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor must not be empty")
    Doctor doctor;

}
