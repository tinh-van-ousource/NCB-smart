package com.tvo.enums;

public enum ServiceRegisterType {
    CARD(1),
    ACCOUNT(2);

    public Integer getType() {
        return type;
    }

    private Integer type;

    ServiceRegisterType(Integer type) {
        this.type = type;
    }
}
