package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchAccountingDTO {

	private List<AccountingDTO> accountings;
	
	private Integer total;

	/**
	 * @return the accountings
	 */
	public List<AccountingDTO> getAccountings() {
		return accountings;
	}

	/**
	 * @param accountings the accountings to set
	 */
	public void setAccountings(List<AccountingDTO> accountings) {
		this.accountings = accountings;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
