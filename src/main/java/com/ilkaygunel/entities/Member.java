package com.ilkaygunel.entities;

import javax.persistence.*;

@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean enabled;
	private String password;
	private String activationToken;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private MemberRoles roleOfMember;

	@Override
	public String toString() {
		return String.format("Member [id=%d, firstName='%s', lastName='%s', email='%s']", id, firstName,
				lastName, email);
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
}
