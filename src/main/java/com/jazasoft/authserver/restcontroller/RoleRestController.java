package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.model.Role;
import com.jazasoft.authserver.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_ROLES)
public class RoleRestController {
    private final Logger logger = LoggerFactory.getLogger(RoleRestController.class);

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> findAllRoles() {
        logger.debug("findAllRoles()");
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_ROLES_ROLE)
    public ResponseEntity<?> findOneRole(@PathVariable("roleId") long id) {
        logger.debug("findOneRole(): id = {}",id);
        Role role = roleService.findOne(id);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_ROLES_ROLE_SEARCH_BY_ROLE_ID)
    public ResponseEntity<?> searchRoleByRoleId(@RequestParam("roleId") String roleId){
        logger.debug("searchRoleByRoleId(): roleId = {}",roleId);
        Role role = roleService.findByRoleId(roleId);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role) {
        logger.debug("saveRole()");
        role = roleService.save(role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_ROLES_ROLE)
    public ResponseEntity<?> updateRole(@PathVariable("roleId") long id,@Validated @RequestBody Role role) {
        logger.debug("updateRole(): id = {}",id);
        if (!roleService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        role.setId(id);
        Role role2 = roleService.update(role);
        return new ResponseEntity<>(role2, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_ROLES_ROLE)
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") long id) {
        logger.debug("deleteRole(): id = {}",id);
        if (!roleService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
