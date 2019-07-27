package com.ilkaygunel.pojo;

import javax.validation.constraints.NotNull;

public class CheckResetPasswordToken {

    @NotNull
    private String token;
    @NotNull
    private String locale;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
