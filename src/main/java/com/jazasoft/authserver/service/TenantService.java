package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.Tenant;
import com.jazasoft.authserver.repository.TenantRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TenantService {
    private final Logger logger = LoggerFactory.getLogger(TenantService.class);

    private final TenantRepository tenantRepository;
    private final Mapper mapper;

    public TenantService(TenantRepository tenantRepository, Mapper mapper) {
        this.tenantRepository = tenantRepository;
        this.mapper = mapper;
    }

    public Tenant findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return tenantRepository.findOne(id);
    }

    public List<Tenant> findAll() {
        logger.debug("findAll()");
        return tenantRepository.findAll();
    }
    

    public Tenant findByTenantId(String tenantId) {
        logger.debug("findByTenantId(): tenantId = {}" , tenantId);
        return tenantRepository.findOneByTenantId(tenantId);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return tenantRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return tenantRepository.count();
    }

    @Transactional
    public Tenant save(Tenant tenant) {
        logger.debug("save()");
        tenant.setEnabled(true);
        return tenantRepository.save(tenant);
    }

    @Transactional
    public Tenant update(Tenant tenant) {
        logger.debug("update()");
        Tenant tenant2 = tenantRepository.findOne(tenant.getId());
        mapper.map(tenant,tenant2);
        return tenant2;
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        Tenant tenant = tenantRepository.findOne(id);
        tenant.setEnabled(false);
    }

}
