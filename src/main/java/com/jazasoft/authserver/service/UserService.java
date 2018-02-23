package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.repository.UserRepository;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        logger.debug("findByUsername(): username = " , username);
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

//    @Transactional
//    public User update(UserDto userDto) {
//        logger.debug("update()");
//        User user = userRepository.findOne(userDto.getId());
//        System.out.println("UserDto = " + userDto);
//        mapper.map(userDto,user);
//        System.out.println("User = " + user);
//        return user;
//    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        User user = userRepository.findOne(id);
        user.setEnabled(false);
    }

//    @Transactional
//    public User saveUserCompany(Long userId, Company company) {
//        logger.debug("saveUserCompany: userId = {}", userId);
//        User user = userRepository.findOne(userId);
//        if (user.getCompany() == null){
//            user.setCompany(company);
//        }else {
//            mapper.map(company, user.getCompany());
//        }
//        company.setUser(user);
//        return user;
//    }
}
