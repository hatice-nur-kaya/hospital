package com.kodhnk.base.services.concretes;

import com.kodhnk.base.dataAccess.DoctorRepository;
import com.kodhnk.base.services.interfaces.IDoctorService;
import org.springframework.stereotype.Service;

@Service
public class DoctorService implements IDoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
}