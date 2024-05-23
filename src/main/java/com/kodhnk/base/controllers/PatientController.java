package com.kodhnk.base.controllers;

import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.entities.Patient;
import com.kodhnk.base.services.concretes.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<DataResult<List<Patient>>> getAllPatients(@RequestParam Long hospitalId) {
        DataResult<List<Patient>> result = patientService.getAllPatients(hospitalId);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}