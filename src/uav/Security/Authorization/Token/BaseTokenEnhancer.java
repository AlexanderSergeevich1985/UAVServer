package uav.Security.Authorization.Token;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

public class BaseTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if(accessToken == null || accessToken instanceof DefaultOAuth2AccessToken) {
            throw new AuthenticationServiceException("Access token is null or not a instance of OAuth2AccessToken");
        }
        Map<String, Object> addsInfo = accessToken.getAdditionalInformation();
        if(!authentication.isAuthenticated()) {
            throw new AuthenticationServiceException("Client isn't authenticated");
        }
        addsInfo.put("Authentication", authentication.getUserAuthentication());
        return accessToken;
    }
}
