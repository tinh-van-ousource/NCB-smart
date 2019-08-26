package com.tvo.enums;

public enum ServiceRegisterServiceType {
    CARD(1),
    LOAN(2),
    ACCOUNT(3),
    SMS(4);

    public Integer getType() {
        return type;
    }

    private Integer type;

    ServiceRegisterServiceType(Integer type) {
        this.type = type;
    }
}
