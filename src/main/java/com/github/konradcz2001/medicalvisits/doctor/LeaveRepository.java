package com.github.konradcz2001.medicalvisits.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

interface LeaveRepository extends JpaRepository<Leave, Long> {
}
