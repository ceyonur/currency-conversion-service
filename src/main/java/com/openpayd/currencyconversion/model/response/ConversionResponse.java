package com.openpayd.currencyconversion.model.response;

import com.openpayd.currencyconversion.model.Conversion;

public class ConversionResponse {
	private Conversion conversion;
	private ConversionError error;
	/**
	 * @return the conversion
	 */
	public Conversion getConversion() {
		return conversion;
	}
	/**
	 * @param conversion the conversion to set
	 */
	public void setConversion(Conversion conversion) {
		this.conversion = conversion;
	}
	/**
	 * @return the error
	 */
	public ConversionError getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(ConversionError error) {
		this.error = error;
	}
}
