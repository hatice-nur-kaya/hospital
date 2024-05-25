package com.kodhnk.base.services.interfaces;

import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.entities.Appointment;

import java.util.List;

public interface IAppointmentService {
    DataResult<List<Appointment>> getAllAppointment();
    DataResult<Appointment> getAppointmentById(Long id);
}