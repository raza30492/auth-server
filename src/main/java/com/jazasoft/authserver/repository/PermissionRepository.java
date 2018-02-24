package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByTenantTenantIdAndAppAppIdAndKey(String tenantId, String appId, String key);

    Permission findOneByTenantTenantIdAndAppAppIdAndKeyAndPermissionId(String tenantId, String appId, String key, String permissionId);

    void deleteByTenantTenantIdAndAppAppIdAndKeyAndPermissionId(String tenantId, String appId, String key, String permissionId);
}
