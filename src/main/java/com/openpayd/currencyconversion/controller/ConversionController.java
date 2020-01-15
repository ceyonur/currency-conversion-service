package com.openpayd.currencyconversion.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/conversion")
@Validated
public class ConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateService proxy;

	@Autowired
	private ConversionRepository repository;

	@PostMapping(value="convert", produces = "application/json")
	@ResponseBody
	public ConversionResponse convert(@RequestParam String source, @RequestParam String target, @RequestParam BigDecimal amount) {
		ConversionResponse response = new ConversionResponse();
		try {
			ServiceCurrency cSource = ServiceCurrency.fromCode(source);
			ServiceCurrency cTarget = ServiceCurrency.fromCode(target);
			ExchangeRateResponse erp = proxy.getExchangeRate(cSource, cTarget);
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
		catch (IllegalArgumentException e) {
			response = new ConversionResponse();
			ConversionError ce = new ConversionError(ErrorCodes.ILLEGAL_ARGUMENT_CODE.getCode(), ErrorCodes.ILLEGAL_ARGUMENT_CODE.getType(), e.getMessage());
			response.setError(ce);
			logger.error(ErrorCodes.ILLEGAL_ARGUMENT_CODE.toString(), e);
		}
		

		catch (Exception e) {
			response = new ConversionResponse();
			ConversionError ce = new ConversionError(ErrorCodes.INTERNAL_ERROR_CODE.getCode(), ErrorCodes.INTERNAL_ERROR_CODE.getType(), e.getMessage());
			response.setError(ce);
			logger.error(ErrorCodes.INTERNAL_ERROR_CODE.toString(), e);
		}

		return response;
	}
	
	@GetMapping(value="/all", produces = "application/json")
	@ResponseBody
	public List<Conversion> getAll(){
		return (List<Conversion>) repository.findAll();
	}
	
	@GetMapping(value="/{id}", produces = "application/json")
	@ResponseBody
	public Optional<Conversion> getById(@PathVariable Long id){
		return repository.findById(id);
	}
	
	
	@GetMapping(value="/", produces = "application/json")
	@ResponseBody
	public List<Conversion> getByDate(@RequestParam @Min(0) Long date){
		return repository.findByTransactionDate(new Date(date));
	}
	
	
	@GetMapping(value="/after", produces = "application/json")
	@ResponseBody
	public List<Conversion> getAfterDate(@RequestParam @Min(0) Long date){
		return repository.findByTransactionDateAfter(new Date(date));
	}
	
	@GetMapping(value="/before", produces = "application/json")
	@ResponseBody
	public List<Conversion> getBeforeDate(@RequestParam @Min(0) Long date){
		return repository.findByTransactionDateBefore(new Date(date));
	}
	
	@GetMapping(value="/between", produces = "application/json")
	@ResponseBody
	public List<Conversion> getBetweenDates(@RequestParam @Min(0) Long start, @RequestParam @Min(0) Long end){
		return repository.findByTransactionDateBetween(new Date(start), new Date(end));
	}
}