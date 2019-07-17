package com.ilkaygunel.pojo;

import javax.validation.constraints.NotNull;

public class PasswordUpdatePojo {

    @NotNull(message = "{email.notnull}")
    private String email;
    @NotNull(message = "{oldPassword.notnull}")
    private String oldPassword;
    @NotNull(message = "{newPassword.notnull}")
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
