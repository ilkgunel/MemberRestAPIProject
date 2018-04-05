package com.ilkaygunel.constants;

public enum ConstantFields {

	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN"),
	EMAIL_CHECK_PATTERN("^[(a-zA-Z-0-9-\\\\_\\\\+\\\\.)]+@[(a-z-A-z)]+\\\\.[(a-zA-z)]{2,3}$");

	private String constantField;

	ConstantFields(String constantField){
		this.constantField=constantField;
	}

	public String getConstantField(){
		return constantField;
	}
}
