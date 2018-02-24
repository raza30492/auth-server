package com.jazasoft.authserver.service;

import com.jazasoft.authserver.model.Resource;
import com.jazasoft.authserver.repository.ResourceRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResourceService {
    private final Logger logger = LoggerFactory.getLogger(ResourceService.class);

    private final ResourceRepository resourceRepository;
    private final Mapper mapper;

    public ResourceService(ResourceRepository resourceRepository, Mapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Resource findOne(Long id) {
        logger.debug("findOne(): id = {}",id);
        return resourceRepository.findOne(id);
    }

    public List<Resource> findAll() {
        logger.debug("findAll()");
        return resourceRepository.findAll();
    }
    

    public Resource findByResourceId(String resourceId) {
        logger.debug("findByResourceId(): resourceId = {}" , resourceId);
        return resourceRepository.findOneByResourceId(resourceId);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return resourceRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return resourceRepository.count();
    }

    @Transactional
    public Resource save(Resource resource) {
        logger.debug("save()");
        resource.setEnabled(true);
        return resourceRepository.save(resource);
    }

    @Transactional
    public Resource update(Resource resource) {
        logger.debug("update()");
        Resource resource2 = resourceRepository.findOne(resource.getId());
        mapper.map(resource,resource2);
        return resource2;
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("delete(): id = {}",id);
        Resource resource = resourceRepository.findOne(id);
        resource.setEnabled(false);
    }

}
