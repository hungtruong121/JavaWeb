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
@Table(name="promotion_include")
@NamedQuery(name="PromotionInclude.findAll", query="SELECT p FROM PromotionInclude p")
public class PromotionInclude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "promotion_include_id", unique = true, nullable = false, length = 20)
	private BigInteger promotionIncludeId;
	
	@Column(name="promotion_id", length = 20)
	private BigInteger promotionId;
	
	@Column(name="team_id", length = 20)
	private BigInteger teamId;
	
	@Column(name="promotion_object", length = 20)
	private BigInteger promotionObject; // team_rank_id
	
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
	 * @return the promotionIncludeId
	 */
	public BigInteger getPromotionIncludeId() {
		return promotionIncludeId;
	}

	/**
	 * @param promotionIncludeId the promotionIncludeId to set
	 */
	public void setPromotionIncludeId(BigInteger promotionIncludeId) {
		this.promotionIncludeId = promotionIncludeId;
	}

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
	 * @return the teamId
	 */
	public BigInteger getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the promotionObject
	 */
	public BigInteger getPromotionObject() {
		return promotionObject;
	}

	/**
	 * @param promotionObject the promotionObject to set
	 */
	public void setPromotionObject(BigInteger promotionObject) {
		this.promotionObject = promotionObject;
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
