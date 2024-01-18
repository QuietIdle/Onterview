package com.quiet.onterview.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberTokenResponse {

    String accessToken;
    String refreshToken;
}
