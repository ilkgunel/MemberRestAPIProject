package com.ilkaygunel.exception;

public enum ErrorCodes {
	ERROR_01("ERROR-01"),
	ERROR_02("ERROR-02"),
	ERROR_03("ERROR-03"),
	ERROR_04("ERROR-04"),
	ERROR_05("ERROR-05"),
	ERROR_06("ERROR-06"),
	ERROR_07("ERROR-07");

	private String errorCode;

	ErrorCodes(String errorCode){
		this.errorCode = errorCode;
	}

	public String getErrorCode(){
		return errorCode;
	}
}
