package com.jazasoft.authserver.security;

import com.jazasoft.authserver.Constants;
import com.jazasoft.authserver.model.App;
import com.jazasoft.authserver.model.User;
import com.jazasoft.authserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mdzahidraza on 11/11/17.
 */

public class CustomTokenEnhancer implements TokenEnhancer {
    private final Logger logger = LoggerFactory.getLogger(CustomTokenEnhancer.class);
    private final UserService userService;

    public CustomTokenEnhancer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        logger.trace("Enhancing Token...");
        Map<String, Object> additionalInfo = new HashMap<>();
        String username = authentication.getName();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String appId = request.getParameter("appId");

        User user = userService.findByUsername(username);
        if (user == null) {
            user = userService.findByEmail(username);
        }
        if (user != null) {
            if (user.getTenant() != null) {
                additionalInfo.put(Constants.TENANT_KEY, user.getTenant().getTenantId());
            }
            if (appId != null && user.getAppList() != null) {
                App app = user.getAppList().stream().filter(a -> appId.equalsIgnoreCase(a.getAppId())).findAny().orElse(null);
                if (app != null) {
                    additionalInfo.put(Constants.APP_KEY, app.getAppId());
                    additionalInfo.put(Constants.RESOURCES_KEY, "");
                }
            }
        } else {
            logger.warn("User not found for enhancing token.");
        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}