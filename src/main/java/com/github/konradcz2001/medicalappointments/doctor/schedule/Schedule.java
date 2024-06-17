package com.github.konradcz2001.medicalappointments.doctor.schedule;


import com.github.konradcz2001.medicalappointments.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;


@Entity
@Table(name = "schedules")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //MONDAY
    @Column(name = "monday_start")
    LocalTime mondayStart;
    @Column(name = "monday_end")
    LocalTime mondayEnd;

    //TUESDAY
    @Column(name = "tuesday_start")
    LocalTime tuesdayStart;
    @Column(name = "tuesday_end")
    LocalTime tuesdayEnd;

    //WEDNESDAY
    @Column(name = "wednesday_start")
    LocalTime wednesdayStart;
    @Column(name = "wednesday_end")
    LocalTime wednesdayEnd;

    //THURSDAY
    @Column(name = "thursday_start")
    LocalTime thursdayStart;
    @Column(name = "thursday_end")
    LocalTime thursdayEnd;

    //FRIDAY
    @Column(name = "friday_start")
    LocalTime fridayStart;
    @Column(name = "friday_end")
    LocalTime fridayEnd;

    //SATURDAY
    @Column(name = "saturday_start")
    LocalTime saturdayStart;
    @Column(name = "saturday_end")
    LocalTime saturdayEnd;

    //SUNDAY
    @Column(name = "sunday_start")
    LocalTime sundayStart;
    @Column(name = "sunday_end")
    LocalTime sundayEnd;

    @OneToOne(mappedBy = "schedule")
    Doctor doctor;
}
