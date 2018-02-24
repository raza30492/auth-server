package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.ROOT_URL_APPS)
public class AppRestController {
    private final Logger logger = LoggerFactory.getLogger(AppRestController.class);

    private final AppService appService;

    public AppRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public ResponseEntity<?> findAllApps() {
        logger.debug("findAllApps()");
        List<App> apps = appService.findAll();
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_APPS_APP)
    public ResponseEntity<?> findOneApp(@PathVariable("appId") long id) {
        logger.debug("findOneApp(): id = {}",id);
        App app = appService.findOne(id);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_APPS_APP_SEARCH_BY_APP_ID)
    public ResponseEntity<?> searchAppByAppId(@RequestParam("appId") String appId){
        logger.debug("searchAppByAppId(): appId = {}",appId);
        App app = appService.findByAppId(appId);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveApp(@Valid @RequestBody App app) {
        logger.debug("saveApp()");
        app = appService.save(app);
        return new ResponseEntity<>(app, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_APPS_APP)
    public ResponseEntity<?> updateApp(@PathVariable("appId") long id,@Valid @RequestBody App app) {
        logger.debug("updateApp(): id = {}",id);
        if (!appService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        app.setId(id);
        App app2 = appService.update(app);
        return new ResponseEntity<>(app2, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_APPS_APP)
    public ResponseEntity<Void> deleteApp(@PathVariable("appId") long id) {
        logger.debug("deleteApp(): id = {}",id);
        if (!appService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        appService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
