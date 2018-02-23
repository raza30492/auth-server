package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
