package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
        logger.debug("saveUser():");
        user = userService.save(user);
//        Link selfLink = linkTo(UserRestController.class).slash(user.getId()).withSelfRel();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create(selfLink.getHref()));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    @PutMapping(ApiUrls.URL_USERS_USER_COMPANY)
//    public ResponseEntity<?> saveUserCompany(@PathVariable("userId") Long userId, @Valid @RequestBody Company company){
//        logger.debug("saveUserCompany: userId = {}", userId);
//        User user = userService.saveUserCompany(userId, company);
//        return new ResponseEntity<Object>(userAssembler.toResource(user), HttpStatus.OK);
//    }

//    @PatchMapping(ApiUrls.URL_USERS_USER)
//    public ResponseEntity<?> updateUser(@PathVariable("userId") long id,@Validated @RequestBody UserDto userDto) {
//        logger.debug("updateUser(): id = {}",id);
//        if (!userService.exists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        userDto.setId(id);
//        User user = userService.update(userDto);
//        return new ResponseEntity<>(userAssembler.toResource(user), HttpStatus.OK);
//    }

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
