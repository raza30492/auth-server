package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.model.Tenant;
import com.jazasoft.authserver.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_TENANTS)
public class TenantRestController {
    private final Logger logger = LoggerFactory.getLogger(TenantRestController.class);

    private final TenantService tenantService;

    public TenantRestController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<?> findAllTenants() {
        logger.debug("findAllTenants()");
        List<Tenant> tenants = tenantService.findAll();
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_TENANTS_TENANT)
    public ResponseEntity<?> findOneTenant(@PathVariable("tenantId") long id) {
        logger.debug("findOneTenant(): id = {}",id);
        Tenant tenant = tenantService.findOne(id);
        if (tenant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_TENANTS_TENANT_SEARCH_BY_TENANT_ID)
    public ResponseEntity<?> searchTenantByTenantId(@RequestParam("tenantId") String tenantId){
        logger.debug("searchTenantByTenantId(): tenantId = {}",tenantId);
        Tenant tenant = tenantService.findByTenantId(tenantId);
        if (tenant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveTenant(@Valid @RequestBody Tenant tenant) {
        logger.debug("saveTenant()");
        tenant = tenantService.save(tenant);
        return new ResponseEntity<>(tenant, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_TENANTS_TENANT)
    public ResponseEntity<?> updateTenant(@PathVariable("tenantId") long id,@Validated @RequestBody Tenant tenant) {
        logger.debug("updateTenant(): id = {}",id);
        if (!tenantService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tenant.setId(id);
        Tenant tenant2 = tenantService.update(tenant);
        return new ResponseEntity<>(tenant2, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_TENANTS_TENANT)
    public ResponseEntity<Void> deleteTenant(@PathVariable("tenantId") long id) {
        logger.debug("deleteTenant(): id = {}",id);
        if (!tenantService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tenantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
