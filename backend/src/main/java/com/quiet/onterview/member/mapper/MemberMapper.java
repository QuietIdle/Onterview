package com.quiet.onterview.member.mapper;

import com.quiet.onterview.member.dto.MemberLoginResponse;
import com.quiet.onterview.member.dto.MemberSignupRequest;
import com.quiet.onterview.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member memberSignupRequestToMember(MemberSignupRequest memberSignupRequest) {
        return new Member(0L,
                memberSignupRequest.getEmail(),memberSignupRequest.getPassword(),
                memberSignupRequest.getNickname(),null);
    }

    public MemberLoginResponse memberToMemberLoginResponse(Member member, String accessToken, String refreshToken) {
        return MemberLoginResponse.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
