package com.ilkaygunel.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<MemberRoles> rolesOfMember;

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

	public List<MemberRoles> getRolesOfMember() {
		if(rolesOfMember==null){
			rolesOfMember = new ArrayList<>();
		}
		return rolesOfMember;
	}

	public void setRolesOfMember(List<MemberRoles> rolesOfMember) {
		this.rolesOfMember = rolesOfMember;
	}
}
