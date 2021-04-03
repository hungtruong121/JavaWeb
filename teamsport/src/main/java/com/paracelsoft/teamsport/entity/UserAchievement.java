package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the party database table.
 * 
 */
@Entity
@Table(name="user_achievement")
@NamedQuery(name="UserAchievement.findAll", query="SELECT p FROM UserAchievement p")
public class UserAchievement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_achievement_id", unique = true, nullable = false, length = 20)
	private BigInteger userAchievementId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;
	
	@Column(name="user_achievement_name", length=200)
	private String userAchievementName;
	
	@Column(name="user_achievement_sport")
	private String userAchievementSport;
	
	@Column(name="user_achievement_time", length=200)
	private String userAchievementTime;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
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