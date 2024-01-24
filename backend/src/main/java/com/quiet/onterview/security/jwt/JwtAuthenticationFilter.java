package com.quiet.onterview.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiet.onterview.member.dto.request.MemberLoginRequest;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.security.SecurityMemberDetail;
import com.quiet.onterview.security.jwt.JwtTokenProvider.TokenType;
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
        System.out.println("==========================================");
        System.out.println(this.getClass().getCanonicalName() + "attemptAuthentication() called");
        System.out.println("JWT Authentication Filter : trying Login");

        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequest memberLoginRequest;

        try {
            memberLoginRequest = objectMapper.readValue(request.getInputStream(), MemberLoginRequest.class);
            System.out.println("USER LOGIN REQUEST INFO -> " + memberLoginRequest.toString());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("is authentication null? -> " + (authentication==null));
            System.out.println("attemptAuthentication ended\n========================================");
            System.out.println(authentication.getPrincipal().toString());
            return authentication;
        } catch(Exception e) {
            e.printStackTrace();
        }       return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        SecurityMemberDetail securityMemberDetail = (SecurityMemberDetail) authResult.getPrincipal();
        Member loginMember = securityMemberDetail.getMember();
        System.out.println("Authentication SUCCESS!!! \n USER " + securityMemberDetail.getMember().toString());

        String accessToken = jwtTokenProvider.generateAccessToken(loginMember.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginMember.getEmail());

        Map<String, Object> memberResponse = new LinkedHashMap<>();
        memberResponse.put("memberId", loginMember.getMemberId());
        memberResponse.put("nickname", loginMember.getNickname());
        memberResponse.put("email", loginMember.getEmail());
        memberResponse.put("accessToken",accessToken);
        memberResponse.put("refreshToken", refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = new ObjectMapper().writeValueAsString(memberResponse);
        response.getWriter().write(jsonResponse);
    }
}
