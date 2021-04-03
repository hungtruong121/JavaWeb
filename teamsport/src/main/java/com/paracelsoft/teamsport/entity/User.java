package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT p FROM User p")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, length = 20)
	private BigInteger userId;
	
	@Column(name = "user_role_id", length = 20)
	private BigInteger userRoleId;
	
	@Column(name="user_full_name", length=50)
	private String userFullName;
	
	@Column(name="user_short_introduction", length=200)
	private String userShortIntroduction;
	
	@Column(name="user_mail", length=100)
	private String userMail;
	
	@Column(name = "relative_id", length = 20)
	private BigInteger relativeId;
	
	@Column(name="user_relationship", length = 255)
	private String userRelationship;
	
	@Column(name="user_phone", length=15)
	private String userPhone;

	@Column(name="user_avatar")
	private BigInteger userAvatar = new BigInteger("1"); //uploadId
	
	@Column(name="user_gender", length = 20)
	private String userGender; //Male,Female,Both
	
	@Column(name="user_national", nullable = false)
	private String userNational;
	
	@Column(name="user_address", length = 255)
	private String userAddress;

	@Column(name="user_birth_date")
	private String userBirthDate;
	
	@Column(name="user_weight")
	private BigDecimal userWeight; //kg
	
	@Column(name="user_height")
	private BigDecimal userHeight; //cm
	
	@Column(name="user_preferred_hand")
	private String userPreferredHand; //left, right
	
	@Column(name="user_preferred_foot")
	private String userPreferredFoot; //left, right
	
	@Column(name="user_class")
	private String userClass;
	
	@Column(name="user_dan")
	private String userDan;
	
	@Column(name="active_team")
	private BigInteger activeTeam;
	
	@Column(name="is_active_privacy")
	private BigInteger isActivePrivacy;
	
	@Column(name="is_active")
	private int isActive = 2;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

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
	 * @return the userFullName
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * @param userFullName the userFullName to set
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	/**
	 * @return the userRelationship
	 */
	public String getUserRelationship() {
		return userRelationship;
	}
	/**
	 * @param userRelationship the userRelationship to set
	 */
	public void setUserRelationship(String userRelationship) {
		this.userRelationship = userRelationship;
	}

	/**
	 * @return the userMail
	 */
	public BigInteger getRelativeId() {
		return relativeId;
	}

	/**
	 * @param userMail the userMail to set
	 */
	public void setRelativeId(BigInteger relativeId) {
		this.relativeId = relativeId;
	}

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone the userPhone to set
	 */
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

	/**
	 * @return the userGender
	 */
	public String getUserGender() {
		return userGender;
	}

	/**
	 * @param userGender the userGender to set
	 */
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	/**
	 * @return the userNational
	 */
	public String getUserNational() {
		return userNational;
	}

	/**
	 * @param userNational the userNational to set
	 */
	public void setUserNational(String userNational) {
		this.userNational = userNational;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @param userAddress the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userBirthDate
	 */
	public String getUserBirthDate() {
		return userBirthDate;
	}

	/**
	 * @param userBirthDate the userBirthDate to set
	 */
	public void setUserBirthDate(String userBirthDate) {
		this.userBirthDate = userBirthDate;
	}

	/**
	 * @return the userWeight
	 */
	public BigDecimal getUserWeight() {
		return userWeight;
	}

	/**
	 * @param userWeight the userWeight to set
	 */
	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}

	/**
	 * @return the userHeight
	 */
	public BigDecimal getUserHeight() {
		return userHeight;
	}

	/**
	 * @param userHeight the userHeight to set
	 */
	public void setUserHeight(BigDecimal userHeight) {
		this.userHeight = userHeight;
	}

	/**
	 * @return the userPreferredHand
	 */
	public String getUserPreferredHand() {
		return userPreferredHand;
	}

	/**
	 * @param userPreferredHand the userPreferredHand to set
	 */
	public void setUserPreferredHand(String userPreferredHand) {
		this.userPreferredHand = userPreferredHand;
	}

	/**
	 * @return the userPreferredFoot
	 */
	public String getUserPreferredFoot() {
		return userPreferredFoot;
	}

	/**
	 * @param userPreferredFoot the userPreferredFoot to set
	 */
	public void setUserPreferredFoot(String userPreferredFoot) {
		this.userPreferredFoot = userPreferredFoot;
	}
	
	/**
	 * @return the userClass
	 */
	public String getUserClass() {
		return userClass;
	}

	/**
	 * @param userClass the userClass to set
	 */
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	/**
	 * @return the userDan
	 */
	public String getUserDan() {
		return userDan;
	}

	/**
	 * @param userDan the userDan to set
	 */
	public void setUserDan(String userDan) {
		this.userDan = userDan;
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

	/**
	 * @return the isActivePrivacy
	 */
	public BigInteger getIsActivePrivacy() {
		return isActivePrivacy;
	}

	/**
	 * @param isActivePrivacy the isActivePrivacy to set
	 */
	public void setIsActivePrivacy(BigInteger isActivePrivacy) {
		this.isActivePrivacy = isActivePrivacy;
	}

	/**
	 * @return the userRoleId
	 */
	public BigInteger getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(BigInteger userRoleId) {
		this.userRoleId = userRoleId;
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

}