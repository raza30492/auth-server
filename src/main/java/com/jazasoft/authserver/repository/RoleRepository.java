package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
