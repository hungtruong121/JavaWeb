package com.paracelsoft.teamsport.api.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class UserDTO {
	
	private BigInteger userId;
	
	private BigInteger userRoleId;
	
	private String userFullName;
	
	private String userMail;
	
	private BigInteger relativeId;
	
	private String userRelationship;
	
	private String userPhone;

	private BigInteger userAvatar = new BigInteger("1"); //uploadId
	
	private String userGender; //Male,Female,Both
	
	private String userNational;
	
	private String userAddress;
		
	private BigDecimal userWeight; //kg
	
	private BigDecimal userHeight; //cm
	
	private String userPreferredHand; //left, right
	
	private String userPreferredFoot; //left, right
	
	private String userClass;
	
	private String userDan;
	
	private BigInteger activeTeam;
	
	private BigInteger isActivePrivacy;
	
	private int isActive = 1;
	
	private String password; // set only for request mobile to server
	
	private String oldPassword; // change pass api

	private Date createdDate;
	
	private Date updatedDate;
	
	private BigInteger status; // Active-In-Active-Block
	
	private String userShortIntroduction;
	
	private String userAge;
	
	private String userBirthDay; // dd/mm/yyyy
	
	private List<UserAchievementDTO> userAchievements;
	
	private List<TeamDTO> teamsJoined;
	
	private String positionSport;
	
	private String activeRate; //ti le tham gia cac event
	
	private String teamMemberRole;
	
	private long unReadNotify;
	
	private BigInteger accountingEvidence; // image evidence paid accounting
	
	private Boolean isDoneTask; //check hoàn thành task ở TodoList
	
	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public BigInteger getRelativeId() {
		return relativeId;
	}

	public void setRelativeId(BigInteger relativeId) {
		this.relativeId = relativeId;
	}

	public String getUserRelationship() {
		return userRelationship;
	}

	public void setUserRelationship(String userRelationship) {
		this.userRelationship = userRelationship;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getUserDan() {
		return userDan;
	}

	public void setUserDan(String userDan) {
		this.userDan = userDan;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the userAvatar
	 */
	public BigInteger getUserAvatar() {
		return userAvatar;
	}

	/**
	 * @param userAvatar the userAvatar to set
	 */
	public void setUserAvatar(BigInteger userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserNational() {
		return userNational;
	}

	public void setUserNational(String userNational) {
		this.userNational = userNational;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public BigDecimal getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}

	public BigDecimal getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(BigDecimal userHeight) {
		this.userHeight = userHeight;
	}

	public String getUserPreferredHand() {
		return userPreferredHand;
	}

	public void setUserPreferredHand(String userPreferredHand) {
		this.userPreferredHand = userPreferredHand;
	}

	public String getUserPreferredFoot() {
		return userPreferredFoot;
	}

	public void setUserPreferredFoot(String userPreferredFoot) {
		this.userPreferredFoot = userPreferredFoot;
	}

	/**
	 * @return the activeTeam
	 */
	public BigInteger getActiveTeam() {
		return activeTeam;
	}

	/**
	 * @param activeTeam the activeTeam to set
	 */
	public void setActiveTeam(BigInteger activeTeam) {
		this.activeTeam = activeTeam;
	}

	public BigInteger getIsActivePrivacy() {
		return isActivePrivacy;
	}

	public void setIsActivePrivacy(BigInteger isActivePrivacy) {
		this.isActivePrivacy = isActivePrivacy;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(BigInteger userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserBirthDay() {
		return userBirthDay;
	}

	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * @return the userAchievements
	 */
	public List<UserAchievementDTO> getUserAchievements() {
		return userAchievements;
	}

	/**
	 * @param userAchievements the userAchievements to set
	 */
	public void setUserAchievements(List<UserAchievementDTO> userAchievements) {
		this.userAchievements = userAchievements;
	}

	/**
	 * @return the userShortIntroduction
	 */
	public String getUserShortIntroduction() {
		return userShortIntroduction;
	}

	/**
	 * @param userShortIntroduction the userShortIntroduction to set
	 */
	public void setUserShortIntroduction(String userShortIntroduction) {
		this.userShortIntroduction = userShortIntroduction;
	}

	public BigInteger getStatus() {
		return status;
	}

	public void setStatus(BigInteger status) {
		this.status = status;
	}

	public List<TeamDTO> getTeamsJoined() {
		return teamsJoined;
	}

	public void setTeamsJoined(List<TeamDTO> teamsJoined) {
		this.teamsJoined = teamsJoined;
	}

	/**
	 * @return the positionSport
	 */
	public String getPositionSport() {
		return positionSport;
	}

	/**
	 * @param positionSport the positionSport to set
	 */
	public void setPositionSport(String positionSport) {
		this.positionSport = positionSport;
	}

	/**
	 * @return the activeRate
	 */
	public String getActiveRate() {
		return activeRate;
	}

	/**
	 * @param activeRate the activeRate to set
	 */
	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

	/**
	 * @return the teamMemberRole
	 */
	public String getTeamMemberRole() {
		return teamMemberRole;
	}

	/**
	 * @param teamMemberRole the teamMemberRole to set
	 */
	public void setTeamMemberRole(String teamMemberRole) {
		this.teamMemberRole = teamMemberRole;
	}

	/**
	 * @return the unReadNotify
	 */
	public long getUnReadNotify() {
		return unReadNotify;
	}
	
	/**
	 * @param unReadNotify the unReadNotify to set
	 */
	public void setUnReadNotify(long unReadNotify) {
		this.unReadNotify = unReadNotify;
	}

	public BigInteger getAccountingEvidence() {
		return accountingEvidence;
	}

	public void setAccountingEvidence(BigInteger accountingEvidence) {
		this.accountingEvidence = accountingEvidence;
	}

	public Boolean getIsDoneTask() {
		return isDoneTask;
	}

	public void setIsDoneTask(Boolean isDoneTask) {
		this.isDoneTask = isDoneTask;
	}
	
}
