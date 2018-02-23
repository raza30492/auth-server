package com.jazasoft.authserver.security;

import com.jazasoft.authserver.TokenInterceptorFilter;
import com.jazasoft.authserver.service.UrlInterceptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by mdzahidraza on 10/07/17.
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(OAuth2ResourceServer.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UrlInterceptorService interceptorService;

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new TokenInterceptorFilter(), FilterSecurityInterceptor.class);
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .addFilter(filterSecurityInterceptor())
                //permitting all because security paths verifications are going to be dynamic
                //because of this filter above
                .authorizeRequests().antMatchers("/**").permitAll();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }


    /**
     * Instantiates Bean remoteTokenServices filterSecurityInterceptor, instance of {@link DynamicFilterInvocationSecurityMetadataSource}
     * that intercepts every request to verify security rules. These rules are stored in database and can be formed and verified
     * dynamically.
     *
     * @return {@link FilterSecurityInterceptor} Bean, instance of {@link DynamicFilterInvocationSecurityMetadataSource}
     */
    public FilterSecurityInterceptor filterSecurityInterceptor() {

        DynamicFilterInvocationSecurityMetadataSource dynamicFilter = new DynamicFilterInvocationSecurityMetadataSource(
                new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>());
        dynamicFilter.setInterceptorService(interceptorService);
        FilterSecurityInterceptor filter = new FilterSecurityInterceptor();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAccessDecisionManager(accessDecisionManager());
        filter.setSecurityMetadataSource(dynamicFilter);
        return filter;
    }

    /**
     * Instantiates Bean accessDecisionManager, instance of {@link AffirmativeBased} with {@link ScopeVoter}, {@link RoleVoter}
     * and {@link AuthenticatedVoter}.
     *
     * @return {@link AccessDecisionManager} bean, instance of {@link UnanimousBased}
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        RoleVoter roleVoter = new RoleVoter(){
            @Override
            public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
                logger.trace("Authorities are :");
                authentication.getAuthorities().forEach(auth -> {
                    logger.trace("-$$$- {}", auth.getAuthority());
                });
                logger.trace("Attributes are :");
                attributes.forEach(attr -> {
                    logger.trace("-$$$- {}", attr.getAttribute());
                });
                int vote =  super.vote(authentication, object, attributes);
                logger.trace("vote is: {}", vote);
                return vote;
            }
        };

        return new AffirmativeBased(Arrays.asList(new ScopeVoter(),roleVoter ,new AuthenticatedVoter()));
    }
}
