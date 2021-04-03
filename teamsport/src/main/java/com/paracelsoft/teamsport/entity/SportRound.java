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
@Table(name="sport_round")
@NamedQuery(name="SportRound.findAll", query="SELECT p FROM SportRound p")
public class SportRound implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sport_round_id", unique = true, nullable = false, length = 20)
	private BigInteger sportRoundId;
	
	@Column(name = "sport_id", nullable = false, length = 20)
	private BigInteger sportId;
	
	@Column(name="sport_round_type", length=20)
	private String sportRoundType;
	
	@Column(name="sport_round_name", length=200)
	private String sportRoundName;
	
	@Column(name="sport_round_time")
	private BigDecimal sportRoundTime;
	
	@Column(name="sport_round_description")
	private String sportRoundDescriotion;
	
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
	 * @return the sportRoundId
	 */
	public BigInteger getSportRoundId() {
		return sportRoundId;
	}

	/**
	 * @param sportRoundId the sportRoundId to set
	 */
	public void setSportRoundId(BigInteger sportRoundId) {
		this.sportRoundId = sportRoundId;
	}

	/**
	 * @return the sportId
	 */
	public BigInteger getSportId() {
		return sportId;
	}

	/**
	 * @param sportId the sportId to set
	 */
	public void setSportId(BigInteger sportId) {
		this.sportId = sportId;
	}

	/**
	 * @return the sportRoundType
	 */
	public String getSportRoundType() {
		return sportRoundType;
	}

	/**
	 * @param sportRoundType the sportRoundType to set
	 */
	public void setSportRoundType(String sportRoundType) {
		this.sportRoundType = sportRoundType;
	}

	/**
	 * @return the sportRoundName
	 */
	public String getSportRoundName() {
		return sportRoundName;
	}

	/**
	 * @param sportRoundName the sportRoundName to set
	 */
	public void setSportRoundName(String sportRoundName) {
		this.sportRoundName = sportRoundName;
	}

	/**
	 * @return the sportRoundDescriotion
	 */
	public String getSportRoundDescriotion() {
		return sportRoundDescriotion;
	}

	/**
	 * @param sportRoundDescriotion the sportRoundDescriotion to set
	 */
	public void setSportRoundDescriotion(String sportRoundDescriotion) {
		this.sportRoundDescriotion = sportRoundDescriotion;
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

	/**
	 * @return the sportRoundValue
	 */
	public BigDecimal getSportRoundTime() {
		return sportRoundTime;
	}

	/**
	 * @param sportRoundValue the sportRoundValue to set
	 */
	public void setSportRoundTime(BigDecimal sportRoundTime) {
		this.sportRoundTime = sportRoundTime;
	}
	
}