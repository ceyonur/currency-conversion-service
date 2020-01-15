package com.openpayd.currencyconversion.service.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.openpayd.currencyconversion.model.Conversion;

public interface ConversionRepository extends CrudRepository<Conversion, Long> {

	List<Conversion> findByTransactionDate(Date transactionDate);

	List<Conversion> findByTransactionDateBetween(
			Date transactionDateStart,
			Date transactionDateEnd);

	List<Conversion> findByTransactionDateBefore(Date transactionDate);
	
	List<Conversion> findByTransactionDateAfter(Date transactionDate);
}
