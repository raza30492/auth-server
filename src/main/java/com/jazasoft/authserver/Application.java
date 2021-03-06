package com.jazasoft.authserver;

import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.model.Resource;
import com.jazasoft.authserver.model.Role;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.repository.AppRepository;
import com.jazasoft.authserver.repository.ResourceRepository;
import com.jazasoft.authserver.repository.RoleRepository;
import com.jazasoft.authserver.repository.UserRepository;
import com.jazasoft.authserver.service.RoleService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mdzahidraza on 10/07/17.
 */
@SpringBootApplication
@Transactional
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(
            RoleRepository roleRepository,
            UserRepository userRepository) {

        return (args) -> {
            if (roleRepository.count() == 0) {
                Role master = new Role("master", "Master", "User for managing all tenants.",true);
                Role superUser = new Role("super_user", "Super User", "User for managing all apps purchased by a tenant.",true);
                Role admin = new Role("admin", "Admin", "User for managing specific app.",true);
                master = roleRepository.save(master);
                roleRepository.save(superUser);
                roleRepository.save(admin);
                User user = new User("Md Zahid", "Raza", "zahid7292","zahid7292@gmail.com","Munnu@90067","890430418");
                user.setRoleList(Collections.singleton(master));
                userRepository.save(user);
            }
        };
    }

    @Bean
    public Mapper dozerBeanMapper() {
        List<String> list = new ArrayList<>();
        list.add("dozer_mapping.xml");
        return new DozerBeanMapper(list);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        return messageSource;
    }

    @Bean
    public BearerTokenExtractor tokenExtractor() {
        return new BearerTokenExtractor();
    }

}
