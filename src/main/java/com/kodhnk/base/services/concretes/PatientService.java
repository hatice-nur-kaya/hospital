package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.*;
import com.kodhnk.base.dataAccess.PatientRepository;
import com.kodhnk.base.dto.patients.CreatePatientRequest;
import com.kodhnk.base.dto.patients.UpdatePatientRequest;
import com.kodhnk.base.entities.Hospital;
import com.kodhnk.base.entities.Patient;
import com.kodhnk.base.services.interfaces.IHospitalService;
import com.kodhnk.base.services.interfaces.IPatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final IHospitalService hospitalService;

    public PatientService(PatientRepository patientRepository, IHospitalService hospitalService) {
        this.patientRepository = patientRepository;
        this.hospitalService = hospitalService;
    }

    @Override
    public DataResult<Set<Patient>> getAllPatients(Long hospitalId) {
        DataResult<Hospital> hospitalResult = hospitalService.getById(hospitalId);
        if (hospitalResult.isSuccess()) {
            Hospital hospital = hospitalResult.getData();
            Set<Patient> patients = hospital.getPatients().stream().collect(Collectors.toSet());
            return new SuccessDataResult<>(Response.GET_PATIENT.getMessage(), patients, 200);
        } else {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public DataResult<Patient> getByPatientId(Long hospitalId) {
        Optional<Patient> patient = patientRepository.findById(hospitalId);
        if (patient.isPresent()) {
            return new SuccessDataResult<>(Response.GET_PATIENT.getMessage(), patient.get(), 200);
        } else {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public DataResult<Patient> createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();
        DataResult<Hospital> hospitalDataResult = hospitalService.getById(request.getHospitalId());
        if (!hospitalDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.HOSPITAL_NOT_FOUND.getMessage(), null, 400);
        }
        patient.setHospital(hospitalDataResult.getData());
        BeanUtils.copyProperties(request, patient);
        patientRepository.save(patient);
        return new SuccessDataResult<>(Response.CREATE_PATIENT.getMessage(), patient, 201);
    }

    @Override
    public DataResult<Patient> updatePatient(UpdatePatientRequest request) {
        DataResult<Patient> patientDataResult = getByPatientId(request.getId());
        if (!patientDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
        Patient patient = patientDataResult.getData();
        BeanUtils.copyProperties(request, patient);
        patientRepository.save(patient);
        return new SuccessDataResult<>(Response.UPDATE_PATIENT.getMessage(), patient, 200);
    }

    @Override
    public Result deletePatient(Long id) {
        DataResult<Patient> patientDataResult = getByPatientId(id);
        if (patientDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
        patientRepository.deleteById(id);
        return new SuccessDataResult<>(Response.DELETE_PATIENT.getMessage(), null, 400);
    }
}