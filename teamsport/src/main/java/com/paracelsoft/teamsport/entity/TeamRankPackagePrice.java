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
@Table(name="team_rank_package_price")
@NamedQuery(name="TeamRankPackagePrice.findAll", query="SELECT p FROM TeamRankPackagePrice p")
public class TeamRankPackagePrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rank_package_price_id", unique = true, nullable = false, length = 20)
	private BigInteger rankPackagePriceId;

	@Column(name = "team_rank_id", nullable = false, length = 20)
	private BigInteger teamRankId;
	
	@Column(name = "rank_package_price_value", nullable = false)
	private BigDecimal rankPackagePriceValue;
	
	@Column(name = "rank_package_price_unit", nullable = false)
	private String rankPackagePriceUnit; //months
	
	@Column(name = "rank_package_price_time", nullable = false)
	private BigDecimal rankPackagePriceTime; //price
	
	@Column(name="created_by")
	private BigInteger createdby;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the rankPackagePriceId
	 */
	public BigInteger getRankPackagePriceId() {
		return rankPackagePriceId;
	}

	/**
	 * @param rankPackagePriceId the rankPackagePriceId to set
	 */
	public void setRankPackagePriceId(BigInteger rankPackagePriceId) {
		this.rankPackagePriceId = rankPackagePriceId;
	}

	/**
	 * @return the teamRankId
	 */
	public BigInteger getTeamRankId() {
		return teamRankId;
	}

	/**
	 * @param teamRankId the teamRankId to set
	 */
	public void setTeamRankId(BigInteger teamRankId) {
		this.teamRankId = teamRankId;
	}

	/**
	 * @return the rankPackagePriceValue
	 */
	public BigDecimal getRankPackagePriceValue() {
		return rankPackagePriceValue;
	}

	/**
	 * @param rankPackagePriceValue the rankPackagePriceValue to set
	 */
	public void setRankPackagePriceValue(BigDecimal rankPackagePriceValue) {
		this.rankPackagePriceValue = rankPackagePriceValue;
	}

	/**
	 * @return the rankPackagePriceUnit
	 */
	public String getRankPackagePriceUnit() {
		return rankPackagePriceUnit;
	}

	/**
	 * @param rankPackagePriceUnit the rankPackagePriceUnit to set
	 */
	public void setRankPackagePriceUnit(String rankPackagePriceUnit) {
		this.rankPackagePriceUnit = rankPackagePriceUnit;
	}

	/**
	 * @return the rankPackagePriceTime
	 */
	public BigDecimal getRankPackagePriceTime() {
		return rankPackagePriceTime;
	}

	/**
	 * @param rankPackagePriceTime the rankPackagePriceTime to set
	 */
	public void setRankPackagePriceTime(BigDecimal rankPackagePriceTime) {
		this.rankPackagePriceTime = rankPackagePriceTime;
	}

	/**
	 * @return the createdby
	 */
	public BigInteger getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
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