package com.openpayd.currencyconversion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.openpayd.currencyconversion.util.ServiceCurrency;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			ServiceCurrency.fromCode(value);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

}
