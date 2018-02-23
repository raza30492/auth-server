package com.jazasoft.authserver;

import com.jazasoft.authserver.model.Role;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.repository.RoleRepository;
import com.jazasoft.authserver.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

/**
 * Created by mdzahidraza on 10/07/17.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //@Bean
    CommandLineRunner init(
            RoleRepository roleRepository,
            UserRepository userRepository) {

        return (args) -> {
            Role master = roleRepository.findOne(1L);
            User user = new User("Md Zahid", "Raza", "zahid7292","zahid7292@gmail.com","Munnu@90067","890430418");
            user.setRoleList(Collections.singleton(master));
            userRepository.save(user);
        };
    }

}
