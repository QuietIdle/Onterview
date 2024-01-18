package com.quiet.onterview.member.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.JwtTokenProvider;
import com.quiet.onterview.common.JwtTokenProvider.TokenType;
import com.quiet.onterview.common.PasswordEncoder;
import com.quiet.onterview.member.dto.MemberLoginRequest;
import com.quiet.onterview.member.dto.MemberLoginResponse;
import com.quiet.onterview.member.dto.MemberModifyPasswordRequest;
import com.quiet.onterview.member.dto.MemberSignupRequest;
import com.quiet.onterview.member.dto.MemberTokenResponse;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.common.ErrorCode;
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

    public void signUpByEmail(MemberSignupRequest memberSignupRequest) {
        if(!isEmailAvailable(memberSignupRequest.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_DUPLICATED);
        }
        if(!isNicknameAvailable(memberSignupRequest.getNickname())) {
            throw new BaseException(ErrorCode.NICKNAME_DUPLICATED);
        }
        if(!isPasswordCorrespond(memberSignupRequest.getPassword(), memberSignupRequest.getConfirm())) {
            throw new BaseException(ErrorCode.PASSWORD_CANNOT_CONFIRM);
        }
        Member member = memberRepository.save(memberMapper.memberSignupRequestToMember(memberSignupRequest));
        updatePassword(member.getMemberId(), member.getPassword());
    }

    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        if(isEmailAvailable(memberLoginRequest.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_NOT_EXISTS);
        }
        Member member = memberRepository.findByEmail(memberLoginRequest.getEmail()).get();
        if(!passwordEncoder.encrypt(member.getMemberId(), memberLoginRequest.getPassword()).equals(member.getPassword())) {
            throw new BaseException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
        String accessToken = jwtTokenProvider.generateToken(TokenType.Access, member.getMemberId());
        String refreshToken = jwtTokenProvider.generateToken(TokenType.Refresh, member.getMemberId());
        return memberMapper.memberToMemberLoginResponse(member, accessToken, refreshToken);
    }

    public void modifyPassword(String accessToken, MemberModifyPasswordRequest memberModifyPasswordRequest) {
        Long userId = jwtTokenProvider.getUserId(accessToken);
        if(!isPasswordCorrespond(memberModifyPasswordRequest.getPassword(), memberModifyPasswordRequest.getConfirm())) {
            throw new BaseException(ErrorCode.PASSWORD_CANNOT_CONFIRM);
        }
        updatePassword(userId, memberModifyPasswordRequest.getPassword());
    }

    public MemberTokenResponse remakeMemberToken(String accessToken, String refreshToken) {
        if(jwtTokenProvider.isValidToken(accessToken)) {
            throw new BaseException(ErrorCode.ACCESS_TOKEN_NOT_EXPIRED);
        }
        if(!jwtTokenProvider.isValidToken(refreshToken)) {
            throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        Long memberId = jwtTokenProvider.getUserId(refreshToken);
        String newAccessToken = jwtTokenProvider.generateToken(TokenType.Access, memberId);
        String newRefreshToken = jwtTokenProvider.generateToken(TokenType.Refresh, memberId);
        return MemberTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void withdrawUser(String accessToken) {
        Long memberId = jwtTokenProvider.getUserId(accessToken);
        memberRepository.deleteById(memberId);
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
