package com.kodhnk.base.dataAccess;

import com.kodhnk.base.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByHospitalId(Long hospitalId);
}