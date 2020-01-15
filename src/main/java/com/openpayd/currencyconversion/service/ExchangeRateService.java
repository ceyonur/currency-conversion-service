package com.openpayd.currencyconversion.service;

import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.util.ServiceCurrency;

public interface ExchangeRateService {
	
	public ExchangeRateResponse getExchangeRate(ServiceCurrency source, ServiceCurrency target);

}
