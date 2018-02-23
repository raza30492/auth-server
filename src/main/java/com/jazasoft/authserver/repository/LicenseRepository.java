package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {
}
