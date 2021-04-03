package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;

public class UserAchievementDTO {
	
	private BigInteger userAchievementId;
	
	private BigInteger userId;
	
	private String userAchievementName;
	
	private BigInteger userAchievementSportId;
	
	private String userAchievementSport;
	
	private String userAchievementTime;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;

	/**
	 * @return the userAchievementId
	 */
	public BigInteger getUserAchievementId() {
		return userAchievementId;
	}

	/**
	 * @param userAchievementId the userAchievementId to set
	 */
	public void setUserAchievementId(BigInteger userAchievementId) {
		this.userAchievementId = userAchievementId;
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

	/**
	 * @return the userAchievementName
	 */
	public String getUserAchievementName() {
		return userAchievementName;
	}

	/**
	 * @param userAchievementName the userAchievementName to set
	 */
	public void setUserAchievementName(String userAchievementName) {
		this.userAchievementName = userAchievementName;
	}

	/**
	 * @return the userAchievementSportId
	 */
	public BigInteger getUserAchievementSportId() {
		return userAchievementSportId;
	}

	/**
	 * @param userAchievementSportId the userAchievementSportId to set
	 */
	public void setUserAchievementSportId(BigInteger userAchievementSportId) {
		this.userAchievementSportId = userAchievementSportId;
	}

	/**
	 * @return the userAchievementSport
	 */
	public String getUserAchievementSport() {
		return userAchievementSport;
	}

	/**
	 * @param userAchievementSport the userAchievementSport to set
	 */
	public void setUserAchievementSport(String userAchievementSport) {
		this.userAchievementSport = userAchievementSport;
	}

	/**
	 * @return the userAchievementTime
	 */
	public String getUserAchievementTime() {
		return userAchievementTime;
	}

	/**
	 * @param userAchievementTime the userAchievementTime to set
	 */
	public void setUserAchievementTime(String userAchievementTime) {
		this.userAchievementTime = userAchievementTime;
	}

	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
