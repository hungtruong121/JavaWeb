package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.SportAttribute;
import com.paracelsoft.teamsport.entity.SportPoint;
import com.paracelsoft.teamsport.entity.SportRound;

public class SportDTO {
	
	private BigInteger sportId;
	private String sportName;
	private String positionsLevels; 
	private String competition; //team, single
	private String matchType; //sport category
	private String matchTypeValue; //sport category
	private List<SportRound> sportRound;
	private List<SportPoint> sportScorePenal;
	private List<SportAttribute> sportAttribute;
	private String gender; //Male,Female,Both
	private String ageFrom;  //min age can play sport
	private String ageTo;  //max age can play sport
	private String description;
	private int isActive = 1;
	private Date createdDate;
	private Date updatedDate;
	
	public BigInteger getSportId() {
		return sportId;
	}
	public void setSportId(BigInteger sportId) {
		this.sportId = sportId;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public String getPositionsLevels() {
		return positionsLevels;
	}
	public void setPositionsLevels(String positionsLevels) {
		this.positionsLevels = positionsLevels;
	}
	public String getCompetition() {
		return competition;
	}
	public void setCompetition(String competition) {
		this.competition = competition;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchTypeValue() {
		return matchTypeValue;
	}
	public void setMatchTypeValue(String matchTypeValue) {
		this.matchTypeValue = matchTypeValue;
	}
	public List<SportRound> getSportRound() {
		return sportRound;
	}
	public void setSportRound(List<SportRound> sportRound) {
		this.sportRound = sportRound;
	}
	public List<SportPoint> getSportScorePenal() {
		return sportScorePenal;
	}
	public void setSportScorePenal(List<SportPoint> sportScorePenal) {
		this.sportScorePenal = sportScorePenal;
	}
	public List<SportAttribute> getSportAttribute() {
		return sportAttribute;
	}
	public void setSportAttribute(List<SportAttribute> sportAttribute) {
		this.sportAttribute = sportAttribute;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAgeFrom() {
		return ageFrom;
	}
	public void setAgeFrom(String ageFrom) {
		this.ageFrom = ageFrom;
	}
	public String getAgeTo() {
		return ageTo;
	}
	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIsActive() {
		return isActive;
	}
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
