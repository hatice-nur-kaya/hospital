package com.kodhnk.base.dataAccess;

import com.kodhnk.base.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
}