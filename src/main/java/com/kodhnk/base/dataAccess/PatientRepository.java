package com.kodhnk.base.dataAccess;

import com.kodhnk.base.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}