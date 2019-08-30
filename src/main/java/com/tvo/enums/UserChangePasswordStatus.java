package com.tvo.enums;

public enum UserChangePasswordStatus {
    NOT_YET("0"),
    CHANGED("1");

    public String getType() {
        return type;
    }

    private String type;

    UserChangePasswordStatus(String type) {
        this.type = type;
    }
}
