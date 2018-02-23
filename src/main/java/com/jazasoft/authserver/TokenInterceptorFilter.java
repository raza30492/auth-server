package com.jazasoft.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class TokenInterceptorFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(TokenInterceptorFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Inside TokenInterceptorFilter ...");
        BearerTokenExtractor tokenExtractor = ApplicationContextUtil.getApplicationContext().getBean(BearerTokenExtractor.class);
        TokenStore tokenStore = ApplicationContextUtil.getApplicationContext().getBean(TokenStore.class);

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Authentication oauth = tokenExtractor.extract((HttpServletRequest) servletRequest);
        String token = oauth.getPrincipal().toString();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();
        for (Map.Entry<String, Object> entry: additionalInfo.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
