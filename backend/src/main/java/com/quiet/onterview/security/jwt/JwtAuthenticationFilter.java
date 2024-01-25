package com.quiet.onterview.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiet.onterview.member.dto.request.MemberLoginRequest;
import com.quiet.onterview.member.dto.response.MemberLoginResponse;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager,
            JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = jwtAuthenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequest memberLoginRequest;

        try {
            memberLoginRequest = objectMapper.readValue(request.getInputStream(), MemberLoginRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch(AuthenticationException e) {
            handleSecurityError(response,e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        SecurityUser securityMemberDetail = (SecurityUser) authResult.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(securityMemberDetail.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(securityMemberDetail.getEmail());

        MemberLoginResponse.builder()
                .memberId(securityMemberDetail.getMemberId())
                .nickname(securityMemberDetail.getNickname())
                .email(securityMemberDetail.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = new ObjectMapper().writeValueAsString(
                MemberLoginResponse.builder()
                        .memberId(securityMemberDetail.getMemberId())
                        .nickname(securityMemberDetail.getNickname())
                        .email(securityMemberDetail.getEmail())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
        response.getWriter().write(jsonResponse);
    }

    public void handleSecurityError(HttpServletResponse response,
                                    AuthenticationException exception) {
        SecurityException securityException = (SecurityException) exception;
        response.setStatus(securityException.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(securityException.getMessage());
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
