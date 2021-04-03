package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The persistent class for the party database table.
 * 
 */
@Embeddable
public class AccountingIncludePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "accounting_id", nullable = false, length = 20)
	private BigInteger accountingId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;

	/**
	 * @return the accountingId
	 */
	public BigInteger getAccountingId() {
		return accountingId;
	}

	/**
	 * @param accountingId the accountingId to set
	 */
	public void setAccountingId(BigInteger accountingId) {
		this.accountingId = accountingId;
	}

	/**
	 * @return the userId
	 */
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountingIncludePK)) {
			return false;
		}
		AccountingIncludePK castOther = (AccountingIncludePK)other;
		return 
			this.accountingId.equals(castOther.accountingId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.accountingId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}
