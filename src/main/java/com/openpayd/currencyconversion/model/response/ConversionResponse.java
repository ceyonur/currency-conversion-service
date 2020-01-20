package com.openpayd.currencyconversion.model.response;

import com.openpayd.currencyconversion.model.Conversion;

public class ConversionResponse extends Response{
	private Conversion conversion;
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
}
