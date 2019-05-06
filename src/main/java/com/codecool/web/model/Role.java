package com.codecool.web.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    REGULAR(0), ADMIN(1);

    private int value;
    private static Map map = new HashMap<>();


    private Role(int value) {
        this.value = value;
    }

    static {
        for (Role role : Role.values()) {
            map.put(role.value, role);
        }
    }

    public static Role valueOf(int pageType) {
        return (Role) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
