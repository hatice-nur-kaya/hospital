package com.kodhnk.base.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Appointment extends JpaRepository<Appointment, Integer> {
}