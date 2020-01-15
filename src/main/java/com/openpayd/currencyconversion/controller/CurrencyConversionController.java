package com.openpayd.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openpayd.currencyconversion.model.Conversion;
import com.openpayd.currencyconversion.model.response.ConversionError;
import com.openpayd.currencyconversion.model.response.ConversionResponse;
import com.openpayd.currencyconversion.model.response.ExchangeRateResponse;
import com.openpayd.currencyconversion.service.ExchangeRateService;
import com.openpayd.currencyconversion.service.repository.ConversionRepository;
import com.openpayd.currencyconversion.util.ErrorCodes;
import com.openpayd.currencyconversion.util.ServiceCurrency;

@RestController
@RequestMapping("/api")
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateService proxy;

	@Autowired
	private ConversionRepository repository;

	@GetMapping(value="exchangeRate", produces = "application/json")
	@ResponseBody
	public ExchangeRateResponse getExchangeRate(@RequestParam String source, @RequestParam String target) {
		ExchangeRateResponse response;
		try {
			ServiceCurrency cSource = ServiceCurrency.fromCode(source);
			ServiceCurrency cTarget = ServiceCurrency.fromCode(target);
			response = proxy.getExchangeRate(cSource, cTarget);
		}
		catch (IllegalArgumentException e) {
			response = new ExchangeRateResponse();
			ConversionError ce = new ConversionError(ErrorCodes.ILLEGAL_ARGUMENT_CODE.getCode(), ErrorCodes.ILLEGAL_ARGUMENT_CODE.getType(), e.getMessage());
			response.setError(ce);
		}

		catch (Exception e) {
			response = new ExchangeRateResponse();
			ConversionError ce = new ConversionError(ErrorCodes.INTERNAL_ERROR_CODE.getCode(), ErrorCodes.INTERNAL_ERROR_CODE.getType(), e.getMessage());
			response.setError(ce);
		}


		return response;
	}

	@GetMapping(value="convert", produces = "application/json")
	@ResponseBody
	public ConversionResponse convert(@RequestParam String source, @RequestParam String target, @RequestParam BigDecimal amount) {
		ConversionResponse response = new ConversionResponse();
		try {
			ExchangeRateResponse erp = getExchangeRate(source, target);
			if(erp.getError() != null) {
				response.setError(erp.getError());			
			}
			else {
				Date now = new Date();
				Conversion conversion = new Conversion(erp.getExchangeRate(), erp.getSource() , erp.getTarget(), now, amount);
				response.setConversion(conversion);
				repository.save(conversion);
			} 
		}
		catch (Exception e) {
			ConversionError ce = new ConversionError(ErrorCodes.INTERNAL_ERROR_CODE.getCode(), ErrorCodes.INTERNAL_ERROR_CODE.getType(), e.getMessage());
			response.setError(ce);
		}

		return response;
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public void handleMissingParams(MissingServletRequestParameterException ex) {
	    
	}


}