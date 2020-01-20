package com.openpayd.currencyconversion.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openpayd.currencyconversion.model.Conversion;
import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.service.ExchangeRateService;
import com.openpayd.currencyconversion.service.repository.ConversionRepository;
import com.openpayd.currencyconversion.util.CustomErrorCode;
import com.openpayd.currencyconversion.util.ServiceCurrency;

@WebMvcTest(ConversionController.class)
public class ConversionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ExchangeRateService service;
	
	@MockBean
	private ConversionRepository repository;

	@Test
	public void testConvert() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal rate = new BigDecimal("5.22");
		BigDecimal amount = new BigDecimal("100.2");
		stubExchangeRateService(source,target, rate);
		mvc.perform(post("/api/conversion/convert?source="+source+"&target="+target+"&amount="+amount)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(true)))
		.andExpect(jsonPath("$.conversion.sourceCurrency", is(source)))
		.andExpect(jsonPath("$.conversion.targetCurrency", is(target)))
		.andExpect(jsonPath("$.conversion.targetAmount").value(is(amount.multiply(rate)), BigDecimal.class));

	}
	
	@Test
	public void testConvertIllegalArgument() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal rate = new BigDecimal(5.22);
		String expectedCode = CustomErrorCode.ILLEGAL_ARGUMENT_CODE.getCode();
		stubExchangeRateService(source,target, rate);
		mvc.perform(post("/api/conversion/convert?source="+source+"&target="+target+"&amount=sadsad")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(false)))
		.andExpect(jsonPath("$.error.code", is(expectedCode)));

	}
	
	@Test
	public void testConvertMissingArgument() throws Exception {
		String source = "USD";
		String target = "TRY";
		BigDecimal amount = new BigDecimal(5.22);
		String expectedCode = CustomErrorCode.MISSING_ARGUMENT_CODE.getCode();
		stubExchangeRateService(source,target, amount);
		mvc.perform(post("/api/conversion/convert?source="+source+"&target="+target)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(false)))
		.andExpect(jsonPath("$.error.code", is(expectedCode)));

	}
	
	@Test
	public void testGetAllConversions() throws Exception {
		Conversion usdToTry = new Conversion(new BigDecimal(5), "USD", "TRY", new Date(), new BigDecimal(100));
		Conversion eurToTry = new Conversion(new BigDecimal(6), "EUR", "TRY", new Date(), new BigDecimal(100));
		ArrayList<Conversion> allConversions = new ArrayList<Conversion>();
		allConversions.add(usdToTry);
		allConversions.add(eurToTry);
		when(repository.findAll()).thenReturn(allConversions);
		
		mvc.perform(get("/api/conversion/all")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

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
