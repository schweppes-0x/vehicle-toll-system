package tonydalov.tol.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import tonydalov.tol.exception.ApiException;

import java.io.IOException;

@Slf4j
public class ApiKeyAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final String headerName;

    public ApiKeyAuthFilter(RequestMatcher requiresAuth, String headerName) {
        super(requiresAuth);
        this.headerName = headerName;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String apiKey = request.getHeader(headerName);
        if (apiKey == null) {
            log.info(ApiException.INVALID_API_KEY);
            return null;
        }
        ApiKeyAuthenticationToken authRequest = new ApiKeyAuthenticationToken(apiKey);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}