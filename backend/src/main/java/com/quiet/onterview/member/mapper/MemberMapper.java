package com.quiet.onterview.member.mapper;

import com.quiet.onterview.member.dto.response.MemberLoginResponse;
import com.quiet.onterview.member.dto.request.MemberSignupRequest;
import com.quiet.onterview.member.entity.Member;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member memberSignupRequestToMember(MemberSignupRequest memberSignupRequest) {
        return Member.builder()
                .email(memberSignupRequest.getEmail())
                .password(memberSignupRequest.getPassword())
                .nickname(memberSignupRequest.getNickname())
                .build();
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
