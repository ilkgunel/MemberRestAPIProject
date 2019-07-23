package com.ilkaygunel.exception;

public enum ErrorCodes {
    ERROR_01("ERROR-01"), //
    ERROR_02("ERROR-02"), //
    ERROR_03("ERROR-03"), //
    ERROR_04("ERROR-04"), //
    ERROR_05("ERROR-05"), //
    ERROR_06("ERROR-06"), //
    ERROR_07("ERROR-07"), //
    ERROR_08("ERROR-08"), //
    ERROR_09("ERROR-09"), //
    ERROR_10("ERROR-10"), //
    ERROR_11("ERROR-11"), //
    ERROR_12("ERROR-12"), //
    ERROR_13("ERROR-13"), //
    ERROR_14("ERROR-14"), //
    ERROR_15("ERROR-15"), //
    ERROR_16("ERROR-16"), //
    ERROR_17("ERROR-17");

    private String errorCode;

    ErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
