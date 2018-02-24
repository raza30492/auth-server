package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.model.Resource;
import com.jazasoft.authserver.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_RESOURCES)
public class ResourceRestController {
    private final Logger logger = LoggerFactory.getLogger(ResourceRestController.class);

    private final ResourceService resourceService;

    public ResourceRestController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<?> findAllResources() {
        logger.debug("findAllResources()");
        List<Resource> resources = resourceService.findAll();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_RESOURCES_RESOURCE)
    public ResponseEntity<?> findOneResource(@PathVariable("resourceId") long id) {
        logger.debug("findOneResource(): id = {}",id);
        Resource resource = resourceService.findOne(id);
        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_RESOURCES_RESOURCE_SEARCH_BY_RESOURCE_ID)
    public ResponseEntity<?> searchResourceByResourceId(@RequestParam("resourceId") String resourceId){
        logger.debug("searchResourceByResourceId(): resourceId = {}",resourceId);
        Resource resource = resourceService.findByResourceId(resourceId);
        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveResource(@Valid @RequestBody Resource resource) {
        logger.debug("saveResource()");
        resource = resourceService.save(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_RESOURCES_RESOURCE)
    public ResponseEntity<?> updateResource(@PathVariable("resourceId") long id,@Valid @RequestBody Resource resource) {
        logger.debug("updateResource(): id = {}",id);
        if (!resourceService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resource.setId(id);
        Resource resource2 = resourceService.update(resource);
        return new ResponseEntity<>(resource2, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_RESOURCES_RESOURCE)
    public ResponseEntity<Void> deleteResource(@PathVariable("resourceId") long id) {
        logger.debug("deleteResource(): id = {}",id);
        if (!resourceService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resourceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
