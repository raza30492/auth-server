package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.Constants;
import com.jazasoft.authserver.model.Permission;
import com.jazasoft.authserver.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_PERMISSIONS)
public class PermissionRestController {
    private final Logger logger = LoggerFactory.getLogger(PermissionRestController.class);

    private final PermissionService permissionService;

    public PermissionRestController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<?> findAllPermissions(@RequestParam("key") String key,
                                                HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        String app = (String) request.getAttribute(Constants.APP_KEY);
        logger.debug("deletePermission(): key = {}, tenant = {}, app = {}", key, tenant, app);
        List<Permission> permissions = permissionService.findAll(tenant, app, key);
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_PERMISSIONS_PERMISSION)
    public ResponseEntity<Void> findOnePermission(@RequestParam("key") String key,
                                                  @PathVariable("permissionId") String id,
                                                 HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        String app = (String) request.getAttribute(Constants.APP_KEY);
        logger.debug("deletePermission(): key = {}, tenant = {}, app = {}", key, tenant, app);
        permissionService.delete(tenant, app, key, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> savePermission(@RequestParam("key") String key,
                                            @Valid @RequestBody Permission permission,
                                            HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        String app = (String) request.getAttribute(Constants.APP_KEY);
        logger.debug("savePermission(): key = {}, tenant = {}, app = {}", key, tenant, app);
        permission = permissionService.save(tenant, app, key, permission);
        return new ResponseEntity<>(permission, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_PERMISSIONS_PERMISSION)
    public ResponseEntity<?> updatePermission(@RequestParam("key") String key,
                                              @PathVariable("permissionId") String id,
                                              @Valid @RequestBody Permission permission,
                                              HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        String app = (String) request.getAttribute(Constants.APP_KEY);
        logger.debug("updatePermission(): key = {}, tenant = {}, app = {}, permissionId = {}", key, tenant, app, id);
        permission.setPermissionId(id);
        Permission permission2 = permissionService.update(tenant, app, key, permission);
        return new ResponseEntity<>(permission2, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_PERMISSIONS_PERMISSION)
    public ResponseEntity<Void> deletePermission(@RequestParam("key") String key,
                                                 @PathVariable("permissionId") String id,
                                                 HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        String app = (String) request.getAttribute(Constants.APP_KEY);
        logger.debug("deletePermission(): key = {}, tenant = {}, app = {}", key, tenant, app);
        permissionService.delete(tenant, app, key, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
