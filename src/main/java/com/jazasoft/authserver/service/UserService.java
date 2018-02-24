package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.Role;
import com.jazasoft.authserver.model.Tenant;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.repository.TenantRepository;
import com.jazasoft.authserver.repository.UserRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, TenantRepository tenantRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.mapper = mapper;
    }

    public User findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        logger.debug("findAll()");
        return userRepository.findAll();
    }

    public List<User> findAllAfter(long after) {
        logger.debug("findAllAfter(): after = {}" , after);
        return userRepository.findByModifiedAtGreaterThan(new Date(after));
    }

    public User findByEmail(String email) {
        logger.debug("findByEmail(): email = {}",email);
        return userRepository.findOneByEmail(email);
    }

    public User findByUsername(String username) {
        logger.debug("findByUsername(): username = {}" , username);
        return userRepository.findOneByUsername(username);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return userRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return userRepository.count();
    }

    /**
     * TODO:
     * master: can create all type of user
     * super_user: cannot create master or super_user
     * admin: cannot create master or super user
     *
     * @param role
     * @param tenantId
     * @param user
     * @return
     */
    @Transactional
    public User save(String role, String tenantId, User user) {
        logger.debug("save()");
        if (role.equalsIgnoreCase(Role.ROLE_SUPER_USER) || role.equalsIgnoreCase(Role.ROLE_ADMIN)) {
            Tenant tenant = tenantRepository.findOneByTenantId(tenantId);
            user.setTenant(tenant);
        }
        user.setPassword(user.getMobile());
        if (user.getEnabled() == null) {
            user.setEnabled(false);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        logger.debug("update()");
        User user2 = userRepository.findOne(user.getId());
        mapper.map(user,user2);
        return user2;
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        User user = userRepository.findOne(id);
        user.setEnabled(false);
    }

}
