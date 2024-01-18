package com.quiet.onterview.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResponse {

    Long memberId;
    String nickname;
    String email;
    String accessToken;
    String refreshToken;
}
