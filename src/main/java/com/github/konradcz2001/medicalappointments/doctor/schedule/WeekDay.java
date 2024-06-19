package com.github.konradcz2001.medicalappointments.doctor.schedule;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeekDay {
    LocalTime start;
    LocalTime end;
}
