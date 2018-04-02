package com.ilkaygunel.constants;

public enum ConstantFields {

	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");

	private String constantField;

	ConstantFields(String constantField){
		this.constantField=constantField;
	}

	public String getConstantField(){
		return constantField;
	}
}
