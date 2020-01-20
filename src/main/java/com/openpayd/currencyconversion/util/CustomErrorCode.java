package com.openpayd.currencyconversion.util;

public enum CustomErrorCode {
	MISSING_ARGUMENT_CODE("003", "MISSING_ARGUMENT_CODE"),
	ILLEGAL_ARGUMENT_CODE("002", "ILLEGAL_ARGUMENT_CODE"),
	INTERNAL_ERROR_CODE("001", "INTERNAL_ERROR_CODE");
	
	private final String code;
	private final String type;

	CustomErrorCode(String code, String type) {
		this.code = code;
        this.type = type;
	}

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
    	return code + "-" + type; 
    }
}
