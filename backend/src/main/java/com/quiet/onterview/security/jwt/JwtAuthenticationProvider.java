package com.quiet.onterview.security.jwt;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberDetail;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

//    private final SecurityMemberDetailService securityMemberDetailService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationProvider autenticate");
        System.out.println("GET PRINCIPAL -> " + authentication.getPrincipal());

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저없음~~~"));

        System.out.println("RECEIVED PASDSWORD -> " + password + "\n ENCODED " + passwordEncoder.encode(password));
        System.out.println("ORIGINAL PASSWORD ! -> " + member.getPassword());
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new UsernameNotFoundException("비번ㅇ 트림~~~");
        }

        SecurityMemberDetail securityMemberDetail = new SecurityMemberDetail(member);
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
