package com.jazasoft.authserver.repository;

import com.jazasoft.authserver.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
    App findOneByAppId(String appId);
}
