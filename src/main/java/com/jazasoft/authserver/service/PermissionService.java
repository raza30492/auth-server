package com.jazasoft.authserver.service;

import com.jazasoft.authserver.Constants;
import com.jazasoft.authserver.Utility;
import com.jazasoft.authserver.dto.ResourceScope;
import com.jazasoft.authserver.dto.UrlInterceptor;
import com.jazasoft.authserver.model.*;
import com.jazasoft.authserver.repository.AppRepository;
import com.jazasoft.authserver.repository.ResourceRepository;
import com.jazasoft.authserver.repository.PermissionRepository;
import com.jazasoft.authserver.repository.TenantRepository;
import com.jazasoft.util.Assert;
import com.jazasoft.util.YamlUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PermissionService {
    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    private final PermissionRepository permissionRepository;
    private final TenantRepository tenantRepository;
    private final AppRepository appRepository;

    private Map<String, Set<com.jazasoft.authserver.dto.Permission>> permissionCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public PermissionService(PermissionRepository permissionRepository, TenantRepository tenantRepository, AppRepository appRepository) {
        this.permissionRepository = permissionRepository;
        this.tenantRepository = tenantRepository;
        this.appRepository = appRepository;

        String filename = Utility.getAppHome() + File.separator + "conf" + File.separator + "permission.yml";
        File file = new File(filename);
        try {
            Object object = YamlUtils.getInstance().getProperty(file);
            if (object != null && object instanceof Map) {
                permissionCache = (Map<String, Set<com.jazasoft.authserver.dto.Permission>>)object;

            }
        } catch (FileNotFoundException e) {
            logger.warn("Permission file: {} not found", file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Permission findOne(String tenantId, String appId, String key, String permissionId){
        return permissionRepository.findOneByTenantTenantIdAndAppAppIdAndKeyAndPermissionId(tenantId, appId, key, permissionId);
    }

    public List<Permission> findAll(String tenantId, String appId, String key) {
        logger.debug("findAll()");
        return permissionRepository.findByTenantTenantIdAndAppAppIdAndKey(tenantId, appId, key);
    }

    @Transactional
    public Permission save(String tenantId, String appId, String key, Permission permission) {
        logger.debug("save()");
        Assert.isTrue(permissionCache.containsKey(appId), "No Dynamic Permission supported for app: " + appId);
        Set<String> permissions = permissionCache.get(appId).stream().map(com.jazasoft.authserver.dto.Permission::getKey).collect(Collectors.toSet());
        Assert.isTrue(permissions.contains(key), "Invalid permission key. Permission key: " + key + " not supported for app: " + appId);

        Tenant tenant = tenantRepository.findOneByTenantId(tenantId);
        App app = appRepository.findOneByAppId(appId);
        Assert.notNull(tenant, "Invalid Tenant. Tenant: "+ tenantId +" does not exist.");
        Assert.notNull(app, "Invalid App. App: "+ appId +" does not exist.");
        permission.setTenant(tenant);
        permission.setApp(app);
        permission.setKey(key);
        return permissionRepository.save(permission);
    }

    /**
     * Only Permission name can be updated
     * @param permission
     * @return
     */
    @Transactional
    public Permission update(String tenantId, String appId, String key, Permission permission) {
        logger.debug("update()");
        Permission permission2 = permissionRepository.findOneByTenantTenantIdAndAppAppIdAndKeyAndPermissionId(tenantId, appId, key, permission.getPermissionId());
        Assert.notNull(permission2, "Permission with key = " + key + ", permissionId = " + permission.getId() + " not found");
        permission2.setName(permission.getName());
        return permission2;
    }

    @Transactional
    public void delete(String tenantId, String appId, String key, String permissionId) {
        permissionRepository.deleteByTenantTenantIdAndAppAppIdAndKeyAndPermissionId(tenantId, appId, key, permissionId);
    }

}
