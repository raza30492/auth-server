package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.User;
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
    private final Mapper mapper;

    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
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

    @Transactional
    public User save(User user) {
        logger.debug("save()");
        user.setPassword(user.getMobile());
        user.setEnabled(true);
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
