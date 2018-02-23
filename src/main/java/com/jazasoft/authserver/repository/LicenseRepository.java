package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.model.License;
import com.jazasoft.authserver.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {
    License findOneByAppAndTenant(App app, Tenant tenant);
}
