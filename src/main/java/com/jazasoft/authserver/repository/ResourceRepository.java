package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
