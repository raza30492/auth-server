package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.repository.AppRepository;
import com.jazasoft.authserver.repository.AppRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppService {
    private final Logger logger = LoggerFactory.getLogger(AppService.class);

    private final AppRepository appRepository;
    private final Mapper mapper;

    public AppService(AppRepository appRepository, Mapper mapper) {
        this.appRepository = appRepository;
        this.mapper = mapper;
    }

    public App findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return appRepository.findOne(id);
    }

    public List<App> findAll() {
        logger.debug("findAll()");
        return appRepository.findAll();
    }
    

    public App findByAppId(String appId) {
        logger.debug("findByAppId(): appId = {}" , appId);
        return appRepository.findOneByAppId(appId);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return appRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return appRepository.count();
    }

    @Transactional
    public App save(App app) {
        logger.debug("save()");
        app.setEnabled(true);
        return appRepository.save(app);
    }

    @Transactional
    public App update(App app) {
        logger.debug("update()");
        App app2 = appRepository.findOne(app.getId());
        mapper.map(app,app2);
        return app2;
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        App app = appRepository.findOne(id);
        app.setEnabled(false);
    }

}
