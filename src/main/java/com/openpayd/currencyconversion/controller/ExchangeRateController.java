package com.openpayd.currencyconversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.service.ExchangeRateService;
import com.openpayd.currencyconversion.util.ServiceCurrency;
import com.openpayd.currencyconversion.validator.ValidCurrency;

@RestController
@Validated
@RequestMapping("/api/exchangeRate")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService proxy;

	@GetMapping(value="", produces = "application/json")
	@ResponseBody
	public ExchangeRateResponse getExchangeRate(@RequestParam @ValidCurrency String source, @RequestParam @ValidCurrency String target) {
		ExchangeRateResponse response;
		ServiceCurrency cSource = ServiceCurrency.fromCode(source);
		ServiceCurrency cTarget = ServiceCurrency.fromCode(target);
		response = proxy.getExchangeRate(cSource, cTarget);
		return response;
	}
}