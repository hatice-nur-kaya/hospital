package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.*;
import com.kodhnk.base.dataAccess.DoctorRepository;
import com.kodhnk.base.dto.doctors.CreateDoctorRequest;
import com.kodhnk.base.dto.doctors.UpdateDoctorRequest;
import com.kodhnk.base.entities.Doctor;
import com.kodhnk.base.entities.Hospital;
import com.kodhnk.base.services.interfaces.IDoctorService;
import com.kodhnk.base.services.interfaces.IHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoctorService implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final IHospitalService hospitalService;

    public DoctorService(DoctorRepository doctorRepository, IHospitalService hospitalService) {
        this.doctorRepository = doctorRepository;
        this.hospitalService = hospitalService;
    }

    @Override
    public DataResult<Set<Doctor>> getDoctorsByHospital(Long hospitalId) {
        DataResult<Hospital> hospitalResult = hospitalService.getById(hospitalId);
        if (hospitalResult.isSuccess()) {
            Hospital hospital = hospitalResult.getData();
            Set<Doctor> doctors = hospital.getDoctors().stream().collect(Collectors.toSet());
            return new SuccessDataResult<>(Response.GET_DOCTOR.getMessage(), doctors, 200);
        } else {
            return new ErrorDataResult<>(Response.HOSPITAL_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public DataResult<Doctor> getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            return new SuccessDataResult<>(Response.GET_DOCTOR.getMessage(), doctor.get(), 200);
        } else {
            return new ErrorDataResult<>(Response.DOCTOR_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public Result createHospitalDoctor(CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(request, doctor);
        doctorRepository.save(doctor);
        return new SuccessDataResult<>(Response.CREATE_DOCTOR.getMessage(), doctor, 201);
    }

    @Override
    public Result updateDoctor(UpdateDoctorRequest request) {
        DataResult<Doctor> doctorDataResult = getDoctorById(request.getId());
        if (!doctorDataResult.isSuccess()) {
            return new ErrorDataResult<>(Response.DOCTOR_NOT_FOUND.getMessage(), null, 400);
        }
        Doctor doctor = doctorDataResult.getData();
        BeanUtils.copyProperties(request, doctor);
        doctorRepository.save(doctor);
        return new SuccessDataResult<>(Response.UPDATE_DOCTOR.getMessage(), doctor, 200);
    }

    @Override
    public Result deleteDoctor(Long id) {
        DataResult<Doctor> doctorDataResult = getDoctorById(id);
        if (!doctorDataResult.isSuccess()) {
            return new ErrorResult(Response.DOCTOR_NOT_FOUND.getMessage(), 400);
        }
        doctorRepository.deleteById(id);
        return new SuccessResult(Response.DELETE_DOCTOR.getMessage(), 200);
    }
}