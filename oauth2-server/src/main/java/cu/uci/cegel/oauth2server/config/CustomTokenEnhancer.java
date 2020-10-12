package cu.uci.cegel.oauth2server.config;

import cu.uci.cegel.oauth2server.domain.CustomUser;
import cu.uci.cegel.oauth2server.domain.CustomUserDetailsService;
import cu.uci.cegel.oauth2server.domain.Usuario;
import cu.uci.cegel.oauth2server.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Value("${jasperserver.username}")
    String username;
    @Value("${jasperserver.password}")
    String password;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> aditionalInfo = new HashMap<>();
        String jasperCredentials = username + ":" + password;
        Long id = ((CustomUser) oAuth2Authentication.getUserAuthentication().getPrincipal()).getId();
        String encodeToString = Base64.getEncoder().encodeToString(jasperCredentials.getBytes());
        String idEncoded = Base64.getEncoder().encodeToString(String.valueOf(id).getBytes());
        aditionalInfo.put("jasper", encodeToString);
        aditionalInfo.put("user_id", idEncoded);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(aditionalInfo);
        return oAuth2AccessToken;
    }
}
