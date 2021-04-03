package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.Team;

public class PromotionDTO {

	private BigInteger promotionId;

	private BigInteger teamId;
	
	private List<BigInteger> listTeamApplyPromo;
	
	private List<Team> teamObjects;
	
	private List<TeamRankDTO> promotionObjects;

	private String promotionCode;

	private String promotionTitle;

	private String promotionDescription;

	private BigInteger promotionStatus; // 0:InActive 1:Active

	private List<BigInteger> listPromotionObject; // team_rank_id
	
	private int isUnlimitedTeam;  // 0: NotCheck 1: IsChecked
	
	private String promotionType; // % or $
	
	private String promotionValue; // value discount follow promotionType
	
	private int increaseDuration; // number of days
	
	private String beginDate;
	
	private String endDate;
	
	private Date dateBeginDate;
	
	private Date dateEndDate;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;

	public BigInteger getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(BigInteger promotionId) {
		this.promotionId = promotionId;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	public BigInteger getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(BigInteger promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	public int getIsUnlimitedTeam() {
		return isUnlimitedTeam;
	}

	public void setIsUnlimitedTeam(int isUnlimitedTeam) {
		this.isUnlimitedTeam = isUnlimitedTeam;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(String promotionValue) {
		this.promotionValue = promotionValue;
	}

	public int getIncreaseDuration() {
		return increaseDuration;
	}

	public void setIncreaseDuration(int increaseDuration) {
		this.increaseDuration = increaseDuration;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<BigInteger> getListTeamApplyPromo() {
		return listTeamApplyPromo;
	}

	public void setListTeamApplyPromo(List<BigInteger> listTeamApplyPromo) {
		this.listTeamApplyPromo = listTeamApplyPromo;
	}

	public Date getDateBeginDate() {
		return dateBeginDate;
	}

	public void setDateBeginDate(Date dateBeginDate) {
		this.dateBeginDate = dateBeginDate;
	}

	public Date getDateEndDate() {
		return dateEndDate;
	}

	public void setDateEndDate(Date dateEndDate) {
		this.dateEndDate = dateEndDate;
	}

	public List<Team> getTeamObjects() {
		return teamObjects;
	}

	public void setTeamObjects(List<Team> teamObjects) {
		this.teamObjects = teamObjects;
	}

	public List<TeamRankDTO> getPromotionObjects() {
		return promotionObjects;
	}

	public void setPromotionObjects(List<TeamRankDTO> promotionObjects) {
		this.promotionObjects = promotionObjects;
	}

	public List<BigInteger> getListPromotionObject() {
		return listPromotionObject;
	}

	public void setListPromotionObject(List<BigInteger> listPromotionObject) {
		this.listPromotionObject = listPromotionObject;
	}
	
}
