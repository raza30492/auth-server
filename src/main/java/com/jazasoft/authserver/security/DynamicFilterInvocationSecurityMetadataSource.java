package com.jazasoft.authserver.security;

import com.jazasoft.authserver.dto.Scope;
import com.jazasoft.authserver.dto.UrlInterceptor;
import com.jazasoft.authserver.service.UrlInterceptorService;
import com.jazasoft.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Here, Decision is made about which roles are authorized for a particular resource
 * By default all endpoints are public.
 * In Order to protect specific endpoint, Add the endpoint entry with scope and role in url-interceptor.yml file
 *
 * Created by mdzahidraza on 28/06/17.
 */
public class DynamicFilterInvocationSecurityMetadataSource extends DefaultFilterInvocationSecurityMetadataSource {

    private final Logger logger = LoggerFactory.getLogger(DynamicFilterInvocationSecurityMetadataSource.class);
    private final String ROLE_PREFIX = "ROLE_";

    private UrlInterceptorService interceptorService;

    public DynamicFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
        super(requestMap);
    }

    public void setInterceptorService(UrlInterceptorService interceptorService) {
        this.interceptorService = interceptorService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        String httpMethod = fi.getRequest().getMethod();

        url = getUrl(url);
        UrlInterceptor urlInterceptor = interceptorService.getUrlInterceptor(url);
        if (urlInterceptor != null) {
            Scope scope = urlInterceptor.getScopes().stream().filter(s -> s.getMethod().equalsIgnoreCase(httpMethod)).findAny().orElse(null);
            if (scope != null) {
                return getAttribute(scope.getRoles());
            } else {
                logger.warn("No Scope found corresponding to method = {}", httpMethod);
            }
        } else {
            logger.error("No Url Interceptor found for url = {}", url);
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }


    /**
     * {@link ConfigAttribute} with specific attribute (access rule).
     * Possible values to getAttribute's return:
     *  - IS_AUTHENTICATED_ANONYMOUSLY - No token in the request
     *  - IS_AUTHENTICATED_REMEMBERED
     *  - IS_AUTHENTICATED_FULLY - With a valid token
     *  - SCOPE_<scope> - Token with a specific scope
     *  - ROLE_<role> - Token's user with specific role
     * @author mdzahidraza
     *
     */
    public class DynamicConfigAttribute implements ConfigAttribute {
        private static final long serialVersionUID = 1201502296417220314L;
        private String attribute;
        public DynamicConfigAttribute(String attribute) {
            this.attribute = attribute;
        }
        @Override
        public String getAttribute() {
            /* Possible values to getAttribute's return:
             * IS_AUTHENTICATED_ANONYMOUSLY, IS_AUTHENTICATED_REMEMBERED
             * IS_AUTHENTICATED_FULLY, SCOPE_<scope>, ROLE_<role>
             */
            return this.attribute;
        }
        @Override
        public String toString() {
            return this.attribute;
        }
    }

    private String getUrl(String url) {
        url = url.split("\\?")[0];
        String[] urls = url.split("/");
        Pattern pattern = Pattern.compile("\\d+");
        for (int i = 0; i < urls.length; i++) {
            if (pattern.matcher(urls[i]).matches()) {
                urls[i] = "{\\d+}";
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < urls.length ; i++) {
            builder.append("/").append(urls[i]);
        }
        return builder.toString();
    }

    private Collection<ConfigAttribute> getAttribute(String roles) {
        Collection<ConfigAttribute> attributes = new ArrayList<>();
        List<String> roleList = Utils.getListFromCsv(roles);
        for (String role: roleList) {
            attributes.add(new DynamicConfigAttribute(ROLE_PREFIX + role));
        }
        return attributes;
    }
}
