package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.*;
import com.kodhnk.base.dataAccess.AppointmentRepository;
import com.kodhnk.base.dataAccess.DoctorRepository;
import com.kodhnk.base.dataAccess.HospitalRepository;
import com.kodhnk.base.dataAccess.PatientRepository;
import com.kodhnk.base.dto.appointments.CreateAppointmentRequest;
import com.kodhnk.base.dto.appointments.UpdateAppointmentRequest;
import com.kodhnk.base.entities.*;
import com.kodhnk.base.services.interfaces.IAppointmentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, HospitalRepository hospitalRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public DataResult<List<Appointment>> getAllAppointment() {
        return null;
    }

    @Override
    public DataResult<Appointment> getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            return new SuccessDataResult<>(Response.GET_APPOINTMENT.getMessage(), appointment.get(), 200);
        } else {
            return new ErrorDataResult<>(Response.APPOINTMENT_NOT_FOUND.getMessage(), null, 400);
        }
    }

    @Override
    public DataResult<Appointment> createAppointment(CreateAppointmentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUsername = userDetails.getUsername();  // Kullanıcı adını al
        }

        Optional<Patient> patient = patientRepository.findById(request.getPatientId());
        Optional<Doctor> doctor = doctorRepository.findById(request.getDoctorId());
        Optional<Hospital> hospital = hospitalRepository.findById(request.getHospitalId());

        if (!patient.isPresent() || !doctor.isPresent() || !hospital.isPresent()) {
            return new ErrorDataResult<>(Response.INVALID_SELECTION.getMessage(), null, 400);
        }

        System.out.println("Creating appointment for user: " + currentUsername);

        Appointment newAppointment = new Appointment();
        newAppointment.setPatient(patient.get());
        newAppointment.setDoctor(doctor.get());
        newAppointment.setHospital(hospital.get());
        newAppointment.setAppointmentDate(request.getAppointmentDate());
        newAppointment.setStatus(AppointmentStatus.PLANNED);

        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        return new SuccessDataResult<>(Response.CREATE_APPOINTMENT.getMessage(), savedAppointment, 201);
    }


    @Override
    public Result updateAppointment(UpdateAppointmentRequest request) {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(request.getAppointmentId());
        if (!existingAppointment.isPresent()) {
            return new ErrorResult(Response.APPOINTMENT_NOT_FOUND.getMessage(), 400);
        }

        Appointment appointment = existingAppointment.get();

        Optional<Patient> patient = patientRepository.findById(request.getPatientId());
        if (!patient.isPresent()) {
            return new ErrorResult("Patient not found", 400);
        }

        Optional<Doctor> doctor = doctorRepository.findById(request.getDoctorId());
        if (!doctor.isPresent()) {
            return new ErrorResult("Doctor not found", 400);
        }

        appointment.setPatient(patient.get());
        appointment.setDoctor(doctor.get());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment);
        return new SuccessResult(Response.APPOINTMENT_UPDATE.getMessage(), 200);
    }

    @Override
    public Result cancelAppointment(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            Appointment currentAppointment = appointment.get();
            currentAppointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.save(currentAppointment);
            return new SuccessResult(Response.APPOINTMENT_DELETE.getMessage(), 200);
        }
        return new ErrorResult(Response.APPOINTMENT_NOT_FOUND.getMessage(), 400);
    }
}