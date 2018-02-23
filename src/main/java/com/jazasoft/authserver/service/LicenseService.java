package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.model.License;
import com.jazasoft.authserver.model.Tenant;
import com.jazasoft.authserver.repository.AppRepository;
import com.jazasoft.authserver.repository.LicenseRepository;
import com.jazasoft.authserver.repository.TenantRepository;
import com.jazasoft.util.Utils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@Service
@Transactional(readOnly = false)
public class LicenseService {
    private final Logger LOGGER = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseRepository licenseRepository;
    private final TenantRepository tenantRepository;
    private final AppRepository appRepository;
    private final Mapper mapper;

    public LicenseService(LicenseRepository licenseRepository, TenantRepository tenantRepository, AppRepository appRepository, Mapper mapper) {
        this.licenseRepository = licenseRepository;
        this.tenantRepository = tenantRepository;
        this.appRepository = appRepository;
        this.mapper = mapper;
    }

    public List<License> findAll() {
        LOGGER.debug("findAll");
        return licenseRepository.findAll();
    }

    public License findOne(Long id) {
        LOGGER.debug("findOne: id = {}", id);
        return licenseRepository.findOne(id);
    }

    public boolean exists(Long id) {
        LOGGER.debug("exists: id = {}", id);
        return licenseRepository.exists(id);
    }

    public License activate(String tenantId, String appId, String productCode, String productKey) {
        Tenant tenant = tenantRepository.findOneByTenantId(tenantId);
        App app = appRepository.findOneByAppId(appId);
        if (tenant != null && app != null) {
            License license = licenseRepository.findOneByAppAndTenant(app, tenant);
            if (license != null) {
                if (license.getActive() == null || license.getActive().equals(false)) {
                    license.setActive(true);
                    license.setActivatedOn(new Date());
                }
                return license;
            } else {
                throw new RuntimeException("Invalid License tenant: " + tenant.getName() + ", App: " + app.getAppId() + "]");
            }
        } else {
            throw new RuntimeException("Invalid Tenant tenantId: " + tenantId + ", or AppId: " + appId + "]");
        }
    }

    @Transactional
    public License save(License license) {
        LOGGER.debug("save");
        Long appId =  license.getApp() != null ? license.getApp().getId() : -1L;
        App app = appRepository.findOne(appId);
        if (app != null) {
            String prefix = app.getAppPrefix();
            int x = 10 - prefix.trim().length();
            license.setProductCode(prefix + Utils.getRandomNumber(x));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                builder.append(Utils.getRandomAlphaNemeric(4)).append("-");
            }
            builder.setLength(builder.length()-1);
            license.setProductKey(builder.toString().toUpperCase());
            license.setPurchasedOn(new Date());
            license.setActive(false);
            return licenseRepository.save(license);
        } else {
            throw new RuntimeException("AppId is required for generating license.");
        }
    }

    @Transactional
    public License update(License license) {
        LOGGER.debug("update");
        License license2 = licenseRepository.findOne(license.getId());
        mapper.map(license,license2);
        return license2;
    }
}
