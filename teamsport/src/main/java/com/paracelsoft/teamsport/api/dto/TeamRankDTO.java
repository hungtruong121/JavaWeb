package com.paracelsoft.teamsport.api.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.TeamRankPackagePrice;

public class TeamRankDTO {

	private BigInteger uploadId;
	
	private BigInteger teamRankId;
	
	private String teamRankName;
	
	private BigInteger teamRankAvatar; //uploadId
	
	private BigDecimal storageCapacity;
	
	private int teamRankMemberLimit;
	
	private String teamRankDescription;
	
	private BigInteger createdby;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private BigDecimal rankPackagePriceValue;
	
	private String rankPackagePriceUnit;
	
	private BigDecimal rankPackagePriceTime;
	
	private BigInteger rankPackagePriceId;
	
	private List<TeamRankPackagePrice> teamRankPackagePrices;
	
	public BigDecimal getStorageCapacity() {
		return storageCapacity;
	}

	public void setStorageCapacity(BigDecimal storageCapacity) {
		this.storageCapacity = storageCapacity;
	}

	public int getTeamRankMemberLimit() {
		return teamRankMemberLimit;
	}

	public void setTeamRankMemberLimit(int teamRankMemberLimit) {
		this.teamRankMemberLimit = teamRankMemberLimit;
	}

	public String getTeamRankDescription() {
		return teamRankDescription;
	}

	public void setTeamRankDescription(String teamRankDescription) {
		this.teamRankDescription = teamRankDescription;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
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

	public BigInteger getRankPackagePriceId() {
		return rankPackagePriceId;
	}

	public void setRankPackagePriceId(BigInteger rankPackagePriceId) {
		this.rankPackagePriceId = rankPackagePriceId;
	}

	public BigDecimal getRankPackagePriceValue() {
		return rankPackagePriceValue;
	}

	public void setRankPackagePriceValue(BigDecimal rankPackagePriceValue) {
		this.rankPackagePriceValue = rankPackagePriceValue;
	}

	public String getRankPackagePriceUnit() {
		return rankPackagePriceUnit;
	}

	public void setRankPackagePriceUnit(String rankPackagePriceUnit) {
		this.rankPackagePriceUnit = rankPackagePriceUnit;
	}

	public BigDecimal getRankPackagePriceTime() {
		return rankPackagePriceTime;
	}

	public void setRankPackagePriceTime(BigDecimal rankPackagePriceTime) {
		this.rankPackagePriceTime = rankPackagePriceTime;
	}

	public BigInteger getTeamRankId() {
		return teamRankId;
	}

	public void setTeamRankId(BigInteger teamRankId) {
		this.teamRankId = teamRankId;
	}

	public List<TeamRankPackagePrice> getTeamRankPackagePrices() {
		return teamRankPackagePrices;
	}

	public void setTeamRankPackagePrices(List<TeamRankPackagePrice> teamRankPackagePrices) {
		this.teamRankPackagePrices = teamRankPackagePrices;
	}

	public BigInteger getUploadId() {
		return uploadId;
	}

	public void setUploadId(BigInteger uploadId) {
		this.uploadId = uploadId;
	}
	public String getTeamRankName() {
		return teamRankName;
	}

	public void setTeamRankName(String teamRankName) {
		this.teamRankName = teamRankName;
	}

	public BigInteger getTeamRankAvatar() {
		return teamRankAvatar;
	}

	public void setTeamRankAvatar(BigInteger teamRankAvatar) {
		this.teamRankAvatar = teamRankAvatar;
	}

}
