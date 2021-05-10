package io.pivotal.microservices.services.web;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonRootName;


@JsonRootName("Account")
public class Account {

	protected Long id;
	protected String number;
	protected String owner;
	protected BigDecimal balance;

	
	protected Account() {
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	
	protected void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	protected void setBalance(BigDecimal value) {
		balance = value;
		balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return number + " [" + owner + "]: $" + balance;
	}

}
