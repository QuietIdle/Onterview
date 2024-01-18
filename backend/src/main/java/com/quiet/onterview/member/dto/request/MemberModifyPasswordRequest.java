package com.quiet.onterview.member.dto.request;

import lombok.Getter;

@Getter
public class MemberModifyPasswordRequest {

    String password;
    String confirm;
}
