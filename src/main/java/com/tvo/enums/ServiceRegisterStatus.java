package com.tvo.enums;

public enum ServiceRegisterStatus {
    NEW(1),
    PROCESSING(2),
    CLOSED(3);

    public Integer getType() {
        return type;
    }

    private Integer type;

    ServiceRegisterStatus(Integer type) {
        this.type = type;
    }
}
