package com.openpayd.currencyconversion.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.openpayd.currencyconversion.util.ServiceCurrency;

@Entity
public class Conversion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private BigDecimal exchangeRate;
	private String sourceCurrency;
	private String targetCurrency;
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	private BigDecimal sourceAmount;
	@Transient
	private BigDecimal targetAmount;
	/**
	 * @param exchangeRate
	 * @param from
	 * @param target
	 * @param date
	 * @param amount
	 */
	public Conversion(BigDecimal exchangeRate, String from, String target, Date transactionDate,
			BigDecimal amount) {
		this.exchangeRate = exchangeRate;
		this.sourceCurrency = from;
		this.targetCurrency = target;
		this.transactionDate = transactionDate;
		this.sourceAmount = amount;
	}
	/**
	 * 
	 */
	protected Conversion() {}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	/**
	 * @return the from
	 */
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	/**
	 * @param from the from to set
	 */
	public void setSourceCurrency(String from) {
		this.sourceCurrency = from;
	}
	/**
	 * @return the target
	 */
	public String getTargetCurrency() {
		return targetCurrency;
	}
	/**
	 * @param target the target to set
	 */
	public void setTargetCurrency(String target) {
		this.targetCurrency = target;
	}
	/**
	 * @return the date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param date the date to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return sourceAmount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.sourceAmount = amount;
	}
	public BigDecimal getTargetAmount() {
		return exchangeRate.multiply(sourceAmount);
	}

}
