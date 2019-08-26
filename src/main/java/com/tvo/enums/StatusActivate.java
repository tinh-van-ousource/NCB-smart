package com.tvo.enums;

public enum StatusActivate {
    STATUS_ACTIVATED("A"), STATUS_DEACTIVATED("D");

    private String status;

    public String getStatus() {
        return status;
    }

    StatusActivate(String status) {
        this.status = status;
    }
}
