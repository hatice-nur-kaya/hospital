package com.kodhnk.base.services.interfaces;

import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.entities.Patient;

import java.util.List;

public interface IPatientService {
    DataResult<List<Patient>> getAllPatients(Long hospitalId);
}