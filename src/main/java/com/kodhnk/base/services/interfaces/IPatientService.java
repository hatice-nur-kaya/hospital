package com.kodhnk.base.services.interfaces;

import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.core.utilities.Result;
import com.kodhnk.base.dto.patients.CreatePatientRequest;
import com.kodhnk.base.dto.patients.UpdatePatientRequest;
import com.kodhnk.base.entities.Patient;

import java.util.List;

public interface IPatientService {
    DataResult<List<Patient>> getAllPatients(Long hospitalId);

    DataResult<List<Patient>> getByPatientId(Long hospitalId);

    DataResult<Patient> createPatient(CreatePatientRequest request);

    DataResult<Patient> updatePatient(UpdatePatientRequest request);

    Result deletePatient(Long id);
}