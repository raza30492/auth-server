package com.jazasoft.authserver.restcontroller;

import com.jazasoft.authserver.ApiUrls;
import com.jazasoft.authserver.Constants;
import com.jazasoft.authserver.model.License;
import com.jazasoft.authserver.service.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdzahidraza on 03/07/17.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_LICENSES)
public class LicenseRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(LicenseRestController.class);

    private final LicenseService licenseService;

    public LicenseRestController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    public ResponseEntity<?> listAllLicenses() {
        LOGGER.debug("listAllLicenses()");
        List<License> licenses = licenseService.findAll();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_LICENSES_LICENSE)
    public ResponseEntity<?> getLicense(@PathVariable("licenseId") long id) {
        LOGGER.debug("getLicense(): id = {}",id);
        License license = licenseService.findOne(id);
        if (license == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @PatchMapping(ApiUrls.URL_LICENSES_ACTIVATE)
    public ResponseEntity<?> activateLicense(@RequestParam("productCode") String productCode,
                                             @RequestParam("productKey") String productKey,
                                                HttpServletRequest request) {
        LOGGER.debug("checkLicense(): productCode = {}, productKey = {}",productCode, productKey);
        String tenantId = (String) request.getAttribute(Constants.TENANT_KEY);
        String appId = (String) request.getAttribute(Constants.APP_KEY);
        if (tenantId == null || appId == null) {
            return new ResponseEntity<>("TenantId and AppId is Required for License activation", HttpStatus.FORBIDDEN);
        }
        License license = licenseService.activate(tenantId, appId, productCode, productKey);
        if (license != null) {
            return new ResponseEntity<>(license, HttpStatus.OK);
        } else {
            Map<String, Object> resp = new HashMap<>();
            resp.put("status","PRODUCT_ACTIVATION_FAILED");
            resp.put("message", "Invalid Product Key");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @GetMapping(ApiUrls.URL_LICENSES_CHECK)
    public ResponseEntity<?> checkLicense(  @RequestParam("tenantId") String tenantId,
                                            @RequestParam("appId") String appId,
                                            @RequestParam("productCode") String productCode,
                                            @RequestParam("productKey") String productKey) {
        LOGGER.debug("checkLicense(): productCode = {}, productKey = {}",productCode, productKey);
        License license = licenseService.activate(tenantId, appId, productCode, productKey);
        if (license == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLicense(@Valid @RequestBody License license) {
        LOGGER.debug("createLicense():\n {}", license.toString());
        license = licenseService.save(license);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @PutMapping(ApiUrls.URL_LICENSES_LICENSE)
    public ResponseEntity<?> updateLicense(@PathVariable("licenseId") long id,@Valid @RequestBody License license) {
        LOGGER.debug("updateLicense(): id = {} \n {}",id,license);
        if (!licenseService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        license.setId(id);
        license = licenseService.update(license);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }


}
