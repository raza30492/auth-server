package com.jazasoft.authserver.security;

import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class WebSecurityGlobalConfig extends GlobalAuthenticationConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebSecurityGlobalConfig.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            logger.trace("Looking for user for {}", username);
            try {
                User user = userRepository.findOneByUsername(username);
                if (user == null) {
                    user = userRepository.findOneByEmail(username);
                    if (user == null) {
                        logger.info("User not found for {}", username);
                        throw new UsernameNotFoundException("user not found");
                    }
                }
                logger.trace("Found user for {}", username);
                //user.setTenant(user.getCompany() != null ? user.getCompany().getDbName() : IConstants.TENANT_MASTER);
                return user;
            } catch (Exception e) {
                logger.error("Error loading user {}", username, e);
            }
            return null;
        };
    }

}
