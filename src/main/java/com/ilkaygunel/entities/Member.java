package com.ilkaygunel.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    @NotNull(message = "{firstName.notnull}")
    @Size(min = 2, message = "First Name should have atleast 2 characters")
    private String firstName;
    @Column(nullable = false)
    @NotNull(message = "{lastName.notnull}")
    @Size(min = 2, message = "Last Name should have atleast 2 characters")
    private String lastName;
    @Column(nullable = false, updatable = false)
    private String email;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @NotNull(message = "Member Language Code Can Not Be Empty")
    private String memberLanguageCode;
    @Column(updatable = false)
    @JsonIgnore
    private String activationToken;
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime activationTokenExpDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private MemberRoles roleOfMember;

    @Override
    public String toString() {
        return String.format("Member [id=%d, firstName='%s', lastName='%s', email='%s']", id, firstName, lastName,
                email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MemberRoles getRoleOfMember() {
        return roleOfMember;
    }

    public void setRoleOfMember(MemberRoles roleOfMember) {
        this.roleOfMember = roleOfMember;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public LocalDateTime getActivationTokenExpDate() {
        return activationTokenExpDate;
    }

    public void setActivationTokenExpDate(LocalDateTime activationTokenExpDate) {
        this.activationTokenExpDate = activationTokenExpDate;
    }

    public String getMemberLanguageCode() {
        return memberLanguageCode;
    }

    public void setMemberLanguageCode(String memberLanguageCode) {
        this.memberLanguageCode = memberLanguageCode;
    }
}
