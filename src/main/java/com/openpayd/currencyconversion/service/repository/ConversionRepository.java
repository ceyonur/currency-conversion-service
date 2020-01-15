package com.openpayd.currencyconversion.service.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.openpayd.currencyconversion.model.Conversion;

public interface ConversionRepository extends CrudRepository<Conversion, Long> {

	Conversion findById(long id);

	List<Conversion> findByTransactionDate(Date transactionDate);

	List<Conversion> findByTransactionDateBetween(
			Date transactionDateStart,
			Date transactionDateEnd);

	List<Conversion> findByTransactionDateBefore(Date transactionDate);
	
	List<Conversion> findByTransactionDateAfter(Date transactionDate);
}
