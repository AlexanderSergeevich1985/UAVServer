/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Security.Authorization.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import uav.Security.Authorization.Token.BaseTokenEnhancer;
import uav.Security.Authorization.Token.BaseTokenServices;
import uav.Utils.security;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableAuthorizationServer
@PropertySource("classpath:security.properties")
public class BaseAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    @Value("${public.key.path}")
    private String pathToKey;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("IoT-client")
                .authorizedGrantTypes("client-credentials", "password","refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .secret("android-secret").refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));
        endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
        BaseTokenServices tokenService = new BaseTokenServices();
        tokenService.setTokenStore(endpoints.getTokenStore());
        tokenService.setSupportRefreshToken(false);
        tokenService.setClientDetailsService(endpoints.getClientDetailsService());
        tokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenService.setAccessTokenValiditySeconds(180000);
        endpoints.tokenServices(tokenService);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new BaseTokenEnhancer();
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Base64.getEncoder().encodeToString(security.loadPublickey(pathToKey).getEncoded()));
        return converter;
    }

    /*@Bean
    @Primary
    public AuthorizationServerTokenServices tokenServices() {
        AuthorizationServerTokenServices tokenServices = new BaseTokenServices();
        ((BaseTokenServices) tokenServices).setTokenEnhancer(tokenEnhancer());
        return tokenServices;
    }*/
}
