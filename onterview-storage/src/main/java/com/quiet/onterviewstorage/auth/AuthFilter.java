package com.quiet.onterviewstorage.auth;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@WebFilter(urlPatterns = "/api-file/*")
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
    private final JwtDecoder jwtDecoder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader(AUTHORIZATION);

        try {
            if (jwtDecoder.validateToken(token)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (JwtException e) {
            final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            servletResponse.getWriter().println(e.getMessage());
        }
    }
}
