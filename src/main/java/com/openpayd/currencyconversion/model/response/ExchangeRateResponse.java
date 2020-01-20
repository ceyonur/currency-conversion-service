package com.openpayd.currencyconversion.model.response;

import java.math.BigDecimal;

public class ExchangeRateResponse extends Response{

	private BigDecimal exchangeRate;
	private String source;
	private String target;
	private boolean fromCache = false;
	
	/**
	 * @param exchangeRate
	 * @param error
	 */
	public ExchangeRateResponse(BigDecimal exchangeRate, boolean fromCache, String source, String target) {
		super();
		this.exchangeRate = exchangeRate;
		this.fromCache = fromCache;
		this.source = source;
		this.target = target;
	}

	public ExchangeRateResponse() {
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
