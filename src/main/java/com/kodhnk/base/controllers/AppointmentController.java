package com.kodhnk.base.controllers;

import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.entities.Appointment;
import com.kodhnk.base.services.interfaces.IAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getAllAppointment")
    public ResponseEntity<DataResult<List<Appointment>>> getAllAppointment() {
        DataResult<List<Appointment>> result = appointmentService.getAllAppointment();
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    @GetMapping("/getAppointmentById")
    public ResponseEntity<DataResult<Appointment>> getAppointmentById(@RequestParam Long id) {
        DataResult<Appointment> result = appointmentService.getAppointmentById(id);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}