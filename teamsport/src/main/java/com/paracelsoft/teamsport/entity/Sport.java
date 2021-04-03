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
@Table(name="sport")
@NamedQuery(name="Sport.findAll", query="SELECT p FROM Sport p")
public class Sport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sport_id", unique = true, nullable = false, length = 20)
	private BigInteger sportId;
	
	@Column(name="sport_name", nullable = false, length=200)
	private String sportName;
	
	@Column(name="positions")
	private String positions; 
	
	@Column(name="competition")
	private String competition; //team, single

	@Column(name="match_type")
	private String matchType; //sport category
	
	@Column(name="match_type_value")
	private String matchTypeValue; //sport category
	
	@Column(name="gender", nullable = false, length = 20)
	private String gender; //Male,Female,Both
	
	@Column(name="age_from", nullable = false, length = 20)
	private String ageFrom;  //min age can play sport
	
	@Column(name="age_to", length = 20)
	private String ageTo;  //max age can play sport
	
	@Column(name="description")
	private String description;
	
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
	 * @return the sportName
	 */
	public String getSportName() {
		return sportName;
	}

	/**
	 * @param sportName the sportName to set
	 */
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	/**
	 * @return the positions
	 */
	public String getPositions() {
		return positions;
	}

	/**
	 * @param positions the positions to set
	 */
	public void setPositions(String positions) {
		this.positions = positions;
	}

	/**
	 * @return the competition
	 */
	public String getCompetition() {
		return competition;
	}

	/**
	 * @param competition the competition to set
	 */
	public void setCompetition(String competition) {
		this.competition = competition;
	}

	/**
	 * @return the matchType
	 */
	public String getMatchType() {
		return matchType;
	}

	/**
	 * @param matchType the matchType to set
	 */
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the ageFrom
	 */
	public String getAgeFrom() {
		return ageFrom;
	}

	/**
	 * @param ageFrom the ageFrom to set
	 */
	public void setAgeFrom(String ageFrom) {
		this.ageFrom = ageFrom;
	}

	/**
	 * @return the ageTo
	 */
	public String getAgeTo() {
		return ageTo;
	}

	/**
	 * @param ageTo the ageTo to set
	 */
	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the matchTypeValue
	 */
	public String getMatchTypeValue() {
		return matchTypeValue;
	}

	/**
	 * @param matchTypeValue the matchTypeValue to set
	 */
	public void setMatchTypeValue(String matchTypeValue) {
		this.matchTypeValue = matchTypeValue;
	}

}