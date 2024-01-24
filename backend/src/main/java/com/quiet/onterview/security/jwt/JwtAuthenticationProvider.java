package com.quiet.onterview.security.jwt;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberAuthentication;
import com.quiet.onterview.security.SecurityMemberDetail;
import java.util.Collection;

import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("JwtAuthenticationProvider autenticate");
//        System.out.println("GET PRINCIPAL -> " + authentication.getPrincipal());

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new SecurityException(HttpStatus.BAD_GATEWAY, "입력하신 이메일에 해당하는 유저가 없습니다."));

//        System.out.println("RECEIVED PASDSWORD -> " + password + "\n ENCODED " + passwordEncoder.encode(password));
//        System.out.println("ORIGINAL PASSWORD ! -> " + member.getPassword());
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new SecurityException(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 않습니다.");
        }

//        SecurityUser securityUser = new SecurityUser(member);

//        SecurityMemberDetail securityMemberDetail = new SecurityMemberDetail(securityUser);
        return new SecurityMemberAuthentication(new SecurityUser(member));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
