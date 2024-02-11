package com.quiet.onterview.websocket;

import com.quiet.onterview.member.entity.Member;
import java.security.Principal;
import lombok.Setter;

public class StompUser implements Principal {
    private String name;
    @Setter
    private Member member;

    public StompUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Member getMember() {
        return member;
    }
}
