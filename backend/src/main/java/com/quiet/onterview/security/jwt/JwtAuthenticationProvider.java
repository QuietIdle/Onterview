package com.quiet.onterview.security.jwt;

import com.quiet.onterview.security.SecurityMemberDetail;
import com.quiet.onterview.security.SecurityMemberDetailService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final SecurityMemberDetailService securityMemberDetailService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationProvider autenticate");
        System.out.println("GET PRINCIPAL -> " + authentication.getPrincipal());

        String username = (String) authentication.getPrincipal();
        SecurityMemberDetail securityMemberDetail = (SecurityMemberDetail)  securityMemberDetailService.loadUserByUsername(username);

        return new Authentication() {

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return securityMemberDetail;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
