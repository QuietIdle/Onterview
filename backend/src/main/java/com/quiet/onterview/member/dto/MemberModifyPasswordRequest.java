package com.quiet.onterview.member.dto;

import lombok.Getter;

@Getter
public class MemberModifyPasswordRequest {

    String password;
    String confirm;
}
