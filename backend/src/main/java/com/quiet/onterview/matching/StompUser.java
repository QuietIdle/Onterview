package com.quiet.onterview.matching;

import java.security.Principal;

public class StompUser implements Principal {
    private String name;

    public StompUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
