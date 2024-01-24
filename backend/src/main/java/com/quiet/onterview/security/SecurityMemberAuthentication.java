package com.quiet.onterview.security;

import com.quiet.onterview.member.entity.Member;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class SecurityMemberAuthentication implements Authentication {

    SecurityUser securityUser;

    public SecurityMemberAuthentication(SecurityUser securityUser) {
        this.securityUser = securityUser;
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
        return securityUser;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
