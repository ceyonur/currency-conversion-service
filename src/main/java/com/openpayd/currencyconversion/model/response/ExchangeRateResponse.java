package com.openpayd.currencyconversion.model.response;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRateResponse {

	private BigDecimal exchangeRate;
	private ConversionError error;
	private String source;
	private String target;
	private boolean fromCache = false;
	
	/**
	 * @param exchangeRate
	 * @param error
	 */
	public ExchangeRateResponse(BigDecimal exchangeRate, ConversionError error, boolean fromCache, String source, String target) {
		super();
		this.exchangeRate = exchangeRate;
		this.error = error;
		this.fromCache = fromCache;
		this.source = source;
		this.target = target;
	}

	public ExchangeRateResponse() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @return the exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(BigDecimal convertedValue) {
		this.exchangeRate = convertedValue;
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

	/**
	 * @return the fromCache
	 */
	public boolean isFromCache() {
		return fromCache;
	}

	/**
	 * @param fromCache the fromCache to set
	 */
	public void setFromCache(boolean fromCache) {
		this.fromCache = fromCache;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
}
