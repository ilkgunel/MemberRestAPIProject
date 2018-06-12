package com.member.constants;

public enum ConstantFields {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    EMAIL_CHECK_PATTERN("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    private String constantField;

    ConstantFields(String constantField) {
        this.constantField = constantField;
    }

    public String getConstantField() {
        return constantField;
    }
}