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
@Table(name="promotion")
@NamedQuery(name="Promotion.findAll", query="SELECT p FROM Promotion p")
public class Promotion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "promotion_id", unique = true, nullable = false, length = 20)
	private BigInteger promotionId;
	
	@Column(name = "promotion_code", length = 100)
	private String promotionCode;
	
	@Column(name = "promotion_title", length = 200)
	private String promotionTitle;
	
	@Column(name = "promotion_description")
	private String promotionDescription;
	
	@Column(name="status_id", length = 20)
	private BigInteger statusId; // 0:InActive 1:Active
	
	@Column(name = "is_unlimited_team")
	private int isUnlimitedTeam;  // 0: NotCheck 1: IsChecked
	
	@Column(name = "promotion_type", length = 45)
	private String promotionType; // % or $
	
	@Column(name = "promotion_value", length = 45)
	private String promotionValue; // value discount follow promotionType
	
	@Column(name = "increase_duration")
	private int increaseDuration; // number of days
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="begin_date")
	private Date beginDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="created_by")
	private BigInteger createdBy;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the promotionId
	 */
	public BigInteger getPromotionId() {
		return promotionId;
	}

	/**
	 * @param promotionId the promotionId to set
	 */
	public void setPromotionId(BigInteger promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the promotionCode
	 */
	public String getPromotionCode() {
		return promotionCode;
	}

	/**
	 * @param promotionCode the promotionCode to set
	 */
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	/**
	 * @return the promotionTitle
	 */
	public String getPromotionTitle() {
		return promotionTitle;
	}

	/**
	 * @param promotionTitle the promotionTitle to set
	 */
	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	/**
	 * @return the promotionDescription
	 */
	public String getPromotionDescription() {
		return promotionDescription;
	}

	/**
	 * @param promotionDescription the promotionDescription to set
	 */
	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	/**
	 * @return the statusId
	 */
	public BigInteger getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the isUnlimitedTeam
	 */
	public int getIsUnlimitedTeam() {
		return isUnlimitedTeam;
	}

	/**
	 * @param isUnlimitedTeam the isUnlimitedTeam to set
	 */
	public void setIsUnlimitedTeam(int isUnlimitedTeam) {
		this.isUnlimitedTeam = isUnlimitedTeam;
	}

	/**
	 * @return the promotionType
	 */
	public String getPromotionType() {
		return promotionType;
	}

	/**
	 * @param promotionType the promotionType to set
	 */
	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	/**
	 * @return the promotionValue
	 */
	public String getPromotionValue() {
		return promotionValue;
	}

	/**
	 * @param promotionValue the promotionValue to set
	 */
	public void setPromotionValue(String promotionValue) {
		this.promotionValue = promotionValue;
	}

	/**
	 * @return the increaseDuration
	 */
	public int getIncreaseDuration() {
		return increaseDuration;
	}

	/**
	 * @param increaseDuration the increaseDuration to set
	 */
	public void setIncreaseDuration(int increaseDuration) {
		this.increaseDuration = increaseDuration;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the createdBy
	 */
	public BigInteger getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
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
