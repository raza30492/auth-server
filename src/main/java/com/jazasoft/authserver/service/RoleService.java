package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.Resource;
import com.jazasoft.authserver.model.Role;
import com.jazasoft.authserver.repository.ResourceRepository;
import com.jazasoft.authserver.repository.RoleRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;
    private final Mapper mapper;
    @Autowired
    private ResourceRepository resourceRepository;

    public RoleService(RoleRepository roleRepository, Mapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public Role findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return roleRepository.findOne(id);
    }

    public List<Role> findAll() {
        logger.debug("findAll()");
        return roleRepository.findAll();
    }
    

    public Role findByRoleId(String roleId) {
        logger.debug("findByRoleId(): roleId = {}" , roleId);
        return roleRepository.findOneByRoleId(roleId);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return roleRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return roleRepository.count();
    }

    @Transactional
    public Role save(Role role) {
        logger.debug("save()");
        role.setEnabled(true);
        return roleRepository.save(role);
    }

    @Transactional
    public Role update(Role role) {
        logger.debug("update()");
        Role role2 = roleRepository.findOne(role.getId());
        mapper.map(role,role2);
        return role2;
    }

    @Transactional
    public Role addResource(Long roleId, Long resourceId) {
        Resource resource = resourceRepository.findOne(resourceId);
        Role role = roleRepository.findOne(roleId);
        role.addResource(resource, "read, write");
        return role;
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        Role role = roleRepository.findOne(id);
        role.setEnabled(false);
    }

}
