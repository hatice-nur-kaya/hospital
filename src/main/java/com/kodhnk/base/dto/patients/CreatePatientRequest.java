package com.kodhnk.base.dto.patients;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientRequest {
    private String name;
    private String surname;
    private String phone;
    private Long hospitalId;
}