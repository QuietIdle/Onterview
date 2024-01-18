package com.quiet.onterview.member.service;

import com.quiet.onterview.common.JwtTokenProvider;
import com.quiet.onterview.common.JwtTokenProvider.TokenType;
import com.quiet.onterview.common.PasswordEncoder;
import com.quiet.onterview.member.dto.MemberLoginRequest;
import com.quiet.onterview.member.dto.MemberLoginResponse;
import com.quiet.onterview.member.dto.MemberModifyPasswordRequest;
import com.quiet.onterview.member.dto.MemberSignupRequest;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.exception.MemberException;
import com.quiet.onterview.member.mapper.MemberMapper;
import com.quiet.onterview.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = {Exception.class})
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUpByEmail(MemberSignupRequest memberSignupRequest) throws Exception {
        if(!isEmailAvailable(memberSignupRequest.getEmail())) {
            throw new Exception(MemberException.EMAIL_DUPLICATED.toString());
        }
        if(!isNicknameAvailable(memberSignupRequest.getNickname())) {
            throw new Exception(MemberException.NICKNAME_DUPLICATED.toString());
        }
        if(!isPasswordCorrespond(memberSignupRequest.getPassword(), memberSignupRequest.getConfirm())) {
            throw new Exception(MemberException.PASSWORD_CANNOT_CONFIRM.toString());
        }
        Member member = memberRepository.save(memberMapper.memberSignupRequestToMember(memberSignupRequest));
        updatePassword(member.getMemberId(), member.getPassword());
    }

    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) throws Exception {
        if(isEmailAvailable(memberLoginRequest.getEmail())) {
            throw new Exception(MemberException.EMAIL_NOT_EXISTS.toString());
        }
        Member member = memberRepository.findByEmail(memberLoginRequest.getEmail()).get();
        if(!passwordEncoder.encrypt(member.getMemberId(), memberLoginRequest.getPassword()).equals(member.getPassword())) {
            throw new Exception(MemberException.PASSWORD_NOT_MATCHES.toString());
        }
        String accessToken = jwtTokenProvider.generateToken(TokenType.Access, member.getMemberId());
        String refreshToken = jwtTokenProvider.generateToken(TokenType.Refresh, member.getMemberId());
        return memberMapper.memberToMemberLoginResponse(member, accessToken, refreshToken);
    }

    public void modifyPassword(String accessToken, MemberModifyPasswordRequest memberModifyPasswordRequest) throws Exception {
        Long userId = jwtTokenProvider.getUserId(accessToken);
        if(!isPasswordCorrespond(memberModifyPasswordRequest.getPassword(), memberModifyPasswordRequest.getConfirm())) {
            throw new Exception(MemberException.PASSWORD_NOT_MATCHES.toString());
        }
        updatePassword(userId, memberModifyPasswordRequest.getPassword());
    }

    public boolean isEmailAvailable(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    public boolean isNicknameAvailable(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    public boolean isPasswordCorrespond(String password, String confirm) {
        return password.equals(confirm);
    }

    public int updatePassword(Long userId, String password) {
        return memberRepository.updatePassword(userId, passwordEncoder.encrypt(userId, password));
    }
}
