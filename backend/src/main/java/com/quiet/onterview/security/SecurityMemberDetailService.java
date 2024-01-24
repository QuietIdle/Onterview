package com.quiet.onterview.security;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO : 없을 때 예외처리
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저없음~~~"));

        return new SecurityMemberDetail(member);
    }

    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        // TODO : 없을 때 예외처리
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저없음~~~"));
        if(!member.getPassword().equals(passwordEncoder.encode(password))) {
            throw new UsernameNotFoundException("비번ㅇ 트림~~~");
        }
        return new SecurityMemberDetail(member);
    }
}
