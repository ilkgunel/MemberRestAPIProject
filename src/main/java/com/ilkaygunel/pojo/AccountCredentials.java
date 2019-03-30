package com.ilkaygunel.pojo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AccountCredentials {

    private String username;
    private String password;
    private Collection<GrantedAuthority> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }

}