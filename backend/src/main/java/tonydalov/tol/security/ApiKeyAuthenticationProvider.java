package tonydalov.tol.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import tonydalov.tol.exception.ApiException;

import java.util.List;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.apikey}")
    private String validApiKey;

    @Value("${security.apikey-header}")
    private String apiKeyHeader;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String) authentication.getPrincipal();
        if (!apiKey.equals(validApiKey)) {
            throw ApiException.invalidApiKey();
        }

        return new ApiKeyAuthenticationToken(apiKey, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public String getApiKeyHeader() {
        return apiKeyHeader;
    }
}