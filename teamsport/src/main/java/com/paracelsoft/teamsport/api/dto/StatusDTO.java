package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;

public class StatusDTO {

	private BigInteger statusId;
	
	private String statusName;

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
