package com.openpayd.currencyconversion.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.openpayd.currencyconversion.controller.ExchangeRateController;
import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.service.ExchangeRateService;
import com.openpayd.currencyconversion.util.CustomErrorCode;
import com.openpayd.currencyconversion.util.ServiceCurrency;

@WebMvcTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ExchangeRateService service;

	@Test
	public void testGetExchangeRate() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal amount = new BigDecimal(5.22);
		stubExchangeRateService(source,target, amount);
		mvc.perform(get("/api/exchangeRate?source="+source+"&target="+target)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(true)))
		.andExpect(jsonPath("$.source", is(source)))
		.andExpect(jsonPath("$.target", is(target)))
		.andExpect(jsonPath("$.exchangeRate", is(amount)));

	}
	
	@Test
	public void testGetExchangeRateIllegalArgument() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal amount = new BigDecimal(5.22);
		String expectedCode = CustomErrorCode.ILLEGAL_ARGUMENT_CODE.getCode();
		stubExchangeRateService(source,target, amount);
		mvc.perform(get("/api/exchangeRate?source=NOTEXIST"+"&target="+target)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(false)))
		.andExpect(jsonPath("$.error.code", is(expectedCode)));

	}
	
	@Test
	public void testGetExchangeRateMissingArgument() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal amount = new BigDecimal(5.22);
		String expectedCode = CustomErrorCode.MISSING_ARGUMENT_CODE.getCode();
		stubExchangeRateService(source,target, amount);
		mvc.perform(get("/api/exchangeRate?&target="+target)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(false)))
		.andExpect(jsonPath("$.error.code", is(expectedCode)));

	}

	private void stubExchangeRateService(String source, String target, BigDecimal amount) {
		ExchangeRateResponse erp = new ExchangeRateResponse();
		ServiceCurrency cSource = ServiceCurrency.fromCode(source);
		ServiceCurrency cTarget = ServiceCurrency.fromCode(target);
		erp.setExchangeRate(amount);
		erp.setSource(source);
		erp.setTarget(target);
		when(service.getExchangeRate(cSource, cTarget)).thenReturn(erp);
	}
}
