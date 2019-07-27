package com.ilkaygunel.pojo;

import javax.validation.constraints.NotNull;

public class PasswordResetPojo {

    @NotNull
    private String newPassword;

    @NotNull
    private String repeatedNewPassword;

    @NotNull
    private String locale;

    @NotNull
    private String token;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
