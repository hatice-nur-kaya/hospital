package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.core.utilities.ErrorDataResult;
import com.kodhnk.base.core.utilities.SuccessDataResult;
import com.kodhnk.base.dataAccess.AppointmentRepository;
import com.kodhnk.base.entities.Appointment;
import com.kodhnk.base.services.interfaces.IAppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
}