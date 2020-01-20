package com.openpayd.currencyconversion.util;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.openpayd.currencyconversion.model.response.Error;
import com.openpayd.currencyconversion.model.response.Response;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({ConstraintViolationException.class, NumberFormatException.class})
	protected ResponseEntity<Object> handle(Exception ex) {
		Response response = new Response();
		Error ce = new Error(CustomErrorCode.ILLEGAL_ARGUMENT_CODE.getCode(), CustomErrorCode.ILLEGAL_ARGUMENT_CODE.getType(), ex.getMessage());
		response.setError(ce);
		response.setSuccess(false);
		logger.error(CustomErrorCode.ILLEGAL_ARGUMENT_CODE.toString(), ex);
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.OK);

	}
	
	@ExceptionHandler
	protected ResponseEntity<Object> handle(MissingServletRequestParameterException ex) {
		Response response = new Response();
		Error ce = new Error(CustomErrorCode.MISSING_ARGUMENT_CODE.getCode(), CustomErrorCode.MISSING_ARGUMENT_CODE.getType(), ex.getMessage());
		response.setError(ce);
		response.setSuccess(false);
		logger.error(CustomErrorCode.MISSING_ARGUMENT_CODE.toString(), ex);
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.OK);

	}
	
	

}
