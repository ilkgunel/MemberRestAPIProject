package com.ilkaygunel.pojo;

import javax.validation.constraints.NotNull;

public class PasswordResetTokenPojo {

    @NotNull
    private String email;

    @NotNull
    private String locale;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
