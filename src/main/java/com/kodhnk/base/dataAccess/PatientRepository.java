package com.kodhnk.base.dataAccess;

import com.kodhnk.base.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findAllById(Long hospitalId);
}