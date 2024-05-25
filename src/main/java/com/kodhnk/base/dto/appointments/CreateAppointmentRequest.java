package com.kodhnk.base.dto.appointments;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateAppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private Date appointmentDate;
}