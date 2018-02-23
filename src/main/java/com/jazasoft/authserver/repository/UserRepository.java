package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);

    User findOneByEmail(String email);

    List<User> findByModifiedAtGreaterThan(Date modifiedAt);
}
