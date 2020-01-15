package com.openpayd.currencyconversion.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.openpayd.currencyconversion.model.ServiceExchangeRates;
import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.util.Constants;
import com.openpayd.currencyconversion.util.RatesCache;
import com.openpayd.currencyconversion.util.ServiceCurrency;

@Service
public class ExchangeRateServiceImp implements ExchangeRateService {
	
	@Autowired
    private Environment env;
	
	@Autowired
	private RatesCache cache;	

	@Override
	public ExchangeRateResponse getExchangeRate(ServiceCurrency source, ServiceCurrency target) {

		ExchangeRateResponse exchangeRate = new ExchangeRateResponse();
		
		String rateKey = source.getCode()+target.getCode();
		
		BigDecimal cachedRate = cache.getCachedRate(rateKey);
		if(null != cachedRate) {
			exchangeRate.setExchangeRate(cachedRate);
			exchangeRate.setSource(source.getCode());
			exchangeRate.setTarget(target.getCode());
			System.out.println("Returning data for " + rateKey + " from Cache");
			exchangeRate.setFromCache(true);
			return exchangeRate;
		}
		
		MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<String, String>();
		uriVariables.add("access_key", env.getProperty(Constants.API_KEY));
		uriVariables.add("currencies", target.getCode());
		uriVariables.add("source", source.getCode());
		uriVariables.add("format", "1");
		
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(env.getProperty(Constants.API_BASE_URL)).queryParams(uriVariables).build();

		RestTemplate restTemplate = new RestTemplate();
		ServiceExchangeRates rates = restTemplate.getForObject(uriComponents.toUri(), ServiceExchangeRates.class);
				
		if(rates.getSuccess()) {
			String cRate = rates.getQuotes().get(rateKey);
			BigDecimal bdr = new BigDecimal(cRate);
			exchangeRate.setExchangeRate(bdr);
			exchangeRate.setSource(source.getCode());
			exchangeRate.setTarget(target.getCode());
			cache.cacheRate(rateKey, bdr);
		} else {
			exchangeRate.setError(rates.getError());
			exchangeRate.setExchangeRate(BigDecimal.ZERO);
		}
		
		return exchangeRate;
	}

	

}
