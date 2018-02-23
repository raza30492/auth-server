package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
