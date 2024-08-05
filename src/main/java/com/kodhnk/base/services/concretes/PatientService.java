package com.kodhnk.base.services.concretes;

import com.kodhnk.base.entities.Hospital;
import com.kodhnk.base.services.interfaces.IPatientService;
import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.*;
import com.kodhnk.base.dataAccess.PatientRepository;
import com.kodhnk.base.dataAccess.UserRepository;
import com.kodhnk.base.dto.patients.CreatePatientRequest;
import com.kodhnk.base.dto.patients.UpdatePatientRequest;
import com.kodhnk.base.entities.Patient;
import com.kodhnk.base.entities.User;
import com.kodhnk.base.services.interfaces.IHospitalService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final IHospitalService hospitalService;
    private final BCryptPasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository, IHospitalService hospitalService, BCryptPasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.hospitalService = hospitalService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<Set<Patient>> getAllPatients(Long hospitalId) {
        List<Patient> patients = patientRepository.findAllByHospitalId(hospitalId);
        Set<Patient> patientSet = new HashSet<>(patients);
        return new SuccessDataResult<>(Response.GET_PATIENT.getMessage(), patientSet, 200);
    }

    @Override
    public DataResult<Patient> getByPatientId(Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            return new SuccessDataResult<>(Response.GET_PATIENT.getMessage(), patient.get(), 200);
        } else {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public DataResult<Patient> createPatient(CreatePatientRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ErrorDataResult<>(Response.EMAIL_ALREADY_EXISTS.getMessage(), null, 409);
        }
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setPhone(request.getPhone());
        patient.setBirthDate(request.getBirthDate());

        Set<Hospital> hospitals = new HashSet<>();
        for (Long hospitalId : request.getHospitalIds()) {
            Optional<Hospital> hospitalOptional = hospitalService.getById(hospitalId);
            if (!hospitalOptional.isPresent()) {
                return new ErrorDataResult<>(Response.HOSPITAL_NOT_FOUND.getMessage(), null, 400);
            }
            hospitals.add(hospitalOptional.get());
        }
        patient.setHospitals(hospitals);

        patientRepository.save(patient);
        return new SuccessDataResult<>(Response.CREATE_PATIENT.getMessage(), patient, 200);
    }

    @Override
    public DataResult<Patient> updatePatient(UpdatePatientRequest request) {
        DataResult<Patient> patientDataResult = getByPatientId(request.getId());
        if (!patientDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
        Patient patient = patientDataResult.getData();
        User user = patient.getUser();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Set<Hospital> hospitals = new HashSet<>();
        for (Long hospitalId : request.getHospitalIds()) {
            DataResult<Hospital> hospitalOptional = hospitalService.getById(hospitalId);
            if (!hospitalOptional.isSuccess()) {
                return new ErrorDataResult<>(Response.HOSPITAL_NOT_FOUND.getMessage(), null, 400);
            }
            hospitals.add(hospitalOptional.getData());
        }
        patient.setHospitals(hospitals);

        patient.setPhone(request.getPhone());
        patient.setBirthDate(request.getBirthDate());
        patientRepository.save(patient);

        return new SuccessDataResult<>(Response.UPDATE_PATIENT.getMessage(), patient, 200);
    }
    @Override
    public Result deletePatient(Long id) {
        DataResult<Patient> patientDataResult = getByPatientId(id);
        if (!patientDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.PATIENT_NOT_FOUND.getMessage(), null, 400);
        }
        Patient patient = patientDataResult.getData();
        patientRepository.delete(patient);
        userRepository.delete(patient.getUser());

        return new SuccessDataResult<>(Response.DELETE_PATIENT.getMessage(), patient, 200);
    }
}