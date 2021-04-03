package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;

public class PrivacyDTO {
	
	private BigInteger privacyId;
	
	private String privacyName;

	/**
	 * @return the privacyId
	 */
	public BigInteger getPrivacyId() {
		return privacyId;
	}

	/**
	 * @param privacyId the privacyId to set
	 */
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	/**
	 * @return the privacyName
	 */
	public String getPrivacyName() {
		return privacyName;
	}

	/**
	 * @param privacyName the privacyName to set
	 */
	public void setPrivacyName(String privacyName) {
		this.privacyName = privacyName;
	}
	
}
