package com.github.konradcz2001.medicalappointments.specialization;

import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

/**
 * The "Specialization" class is an entity class that is mapped to a database table named "specializations". It contains the following attributes:
 * - id: an Integer representing the unique identifier of the specialization.
 * - specialization: a String representing the name of the specialization. It is unique and cannot be blank.
 * - doctors: a Set of Doctor objects representing the doctors who have this specialization. It is a many-to-many relationship, mapped by the "specializations" attribute in the Doctor class.
 * <p>
 * The class has the following annotations:
 * - @Entity: indicates that this class is an entity and should be mapped to a database table.
 * - @Table: specifies the name of the database table to which this entity is mapped.
 * - @Data: a Lombok annotation that generates getter and setter methods for all attributes, as well as other useful methods like equals, hashCode, and toString.
 * - @FieldDefaults: a Lombok annotation that sets the default access level for fields to private.
 * - @NoArgsConstructor: a Lombok annotation that generates a no-argument constructor.
 * - @AllArgsConstructor: a Lombok annotation that generates a constructor with all arguments.
 * <p>
 * The "Specialization" class is used to represent a specialization in the medical appointments system. It is associated with doctors who have this specialization.
 */
@Entity
@Table(name = "specializations")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "specialization", unique = true, nullable = false)
    @NotBlank(message = "Specialization must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String specialization;

    //@JsonIgnore
    @ManyToMany(mappedBy = "specializations")
    Set<Doctor> doctors = new HashSet<>();



}
