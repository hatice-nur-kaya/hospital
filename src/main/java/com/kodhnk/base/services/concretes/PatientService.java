package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.utilities.*;
import com.kodhnk.base.dataAccess.PatientRepository;
import com.kodhnk.base.dto.patients.CreatePatientRequest;
import com.kodhnk.base.dto.patients.UpdatePatientRequest;
import com.kodhnk.base.entities.Patient;
import com.kodhnk.base.services.interfaces.IPatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public DataResult<List<Patient>> getAllPatients(Long hospitalId) {
        return  null;
    }

    @Override
    public DataResult<List<Patient>> getByPatientId(Long hospitalId) {
        return null;
    }

    @Override
    public DataResult<Patient> createPatient(CreatePatientRequest request) {
        return null;
    }

    @Override
    public DataResult<Patient> updatePatient(UpdatePatientRequest request) {
        return null;
    }

    @Override
    public Result deletePatient(Long id) {
        return null;
    }
}