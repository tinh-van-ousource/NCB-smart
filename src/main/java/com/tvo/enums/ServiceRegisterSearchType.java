package com.tvo.enums;

public enum ServiceRegisterSearchType {
    CARD(1),
    ACCOUNT(2);

    public Integer getType() {
        return type;
    }

    private Integer type;

    ServiceRegisterSearchType(Integer type) {
        this.type = type;
    }
}
