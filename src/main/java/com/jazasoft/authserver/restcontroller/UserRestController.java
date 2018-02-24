package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.Constants;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.service.UserService;
import com.jazasoft.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * TODO:
 * Master can manage all user
 * Super User can manage user of specific tenant
 * Admin can manage user of specific app
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_USERS)
public class UserRestController {
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers(@RequestParam(value = "after", defaultValue = "0") Long after) {
        logger.debug("findAllUsers()");
        List<User> users = userService.findAllAfter(after);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_USERS_USER)
    public ResponseEntity<?> findOneUser(@PathVariable("userId") long id) {
        logger.debug("findOneUser(): id = {}",id);
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_USERS_USER_SEARCH_BY_EMAIL)
    public ResponseEntity<?> searchUserByEmail(@RequestParam("email") String email){
        logger.debug("searchByName(): name = {}",email);
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @SuppressWarnings("unchecked")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, HttpServletRequest request) {
        String tenant = (String) request.getAttribute(Constants.TENANT_KEY);
        Assert.notNull(request.getAttribute(Constants.ROLES_KEY), "No authority found for user.");
        List<String> roleList = (List<String>) request.getAttribute(Constants.ROLES_KEY);
        Assert.isTrue(roleList.size() == 1, "Only one of [master,super_user,admin] roles expected.");
        String role = roleList.get(0).substring(5);
        logger.debug("saveUser(): role: {}, tenant: {}", role, tenant);

        user = userService.save(role, tenant, user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_USERS_USER)
    public ResponseEntity<?> updateUser(@PathVariable("userId") long id,@Valid @RequestBody User user) {
        logger.debug("updateUser(): id = {}",id);
        if (!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        User user2 = userService.update(user);
        return new ResponseEntity<>(user2, HttpStatus.OK);
    }

    /**
     * User cannot be deleted but can be: deactivated
     * @param id
     * @return
     */
    @DeleteMapping(ApiUrls.URL_USERS_USER)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") long id) {
        logger.debug("deleteUser(): id = {}",id);
        if (!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
