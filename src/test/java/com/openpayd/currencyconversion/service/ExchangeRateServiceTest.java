package com.openpayd.currencyconversion.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.util.ServiceCurrency;

@SpringBootTest
public class ExchangeRateServiceTest {
	@Autowired 
	private ExchangeRateService exchangeRateService;

	@Test
	public void testGetExchangeRate() throws Exception {
		ExchangeRateResponse erp = exchangeRateService.getExchangeRate(ServiceCurrency.USD, ServiceCurrency.TRY);
		assertEquals("USD", erp.getSource());
		assertEquals("TRY", erp.getTarget());
		assertEquals(false, erp.isFromCache());
		assertThat(erp.getExchangeRate()).isInstanceOf((Number.class));
	}

	@Test
	public void testGetExchangeRateFromCache() throws Exception {
		//call multiple times to cache the value
		ExchangeRateResponse erp = new ExchangeRateResponse();
		for (int i = 0; i < 2; i++) {
			erp = exchangeRateService.getExchangeRate(ServiceCurrency.USD, ServiceCurrency.TRY);
		}

		assertEquals("USD", erp.getSource());
		assertEquals("TRY", erp.getTarget());
		assertEquals(true, erp.isFromCache());
		assertThat(erp.getExchangeRate()).isInstanceOf((Number.class));
	}
}
