package com.github.konradcz2001.medicalappointments.doctor.leave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LeaveFacade {
    private final LeaveRepository repository;

    public LeaveFacade(LeaveRepository repository) {
        this.repository = repository;
    }

    public Optional<Leave> findById(Long id) {
        return repository.findById(id);
    }
    public void save(Leave leave) {
        repository.save(leave);
    }
    public void delete(Leave leave) {
        repository.delete(leave);
    }

    public Page<Leave> findAllByDoctorId(Long id, Pageable pageable){
        return repository.findAllByDoctorId(id, pageable);
    }


}
