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
@Table(name="team_rank")
@NamedQuery(name="TeamRank.findAll", query="SELECT p FROM TeamRank p")
public class TeamRank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_rank_id", unique = true, nullable = false, length = 20)
	private BigInteger teamRankId;
	
	@Column(name="team_rank_name", length=100)
	private String teamRankName;
	
	@Column(name="team_rank_avatar")
	private BigInteger teamRankAvatar; //uploadId
	
	@Column(name="team_rank_storage_capacity")
	private BigDecimal storageCapacity; //Giga byte
	
	@Column(name="team_rank_member_limit")
	private int teamRankMemberLimit;
	
	@Column(name="team_rank_description", length=255)
	private String teamRankDescription;
	
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
	 * @return the teamRankName
	 */
	public String getTeamRankName() {
		return teamRankName;
	}

	/**
	 * @param teamRankName the teamRankName to set
	 */
	public void setTeamRankName(String teamRankName) {
		this.teamRankName = teamRankName;
	}

	/**
	 * @return the teamRankAvatar
	 */
	public BigInteger getTeamRankAvatar() {
		return teamRankAvatar;
	}

	/**
	 * @param teamRankAvatar the teamRankAvatar to set
	 */
	public void setTeamRankAvatar(BigInteger teamRankAvatar) {
		this.teamRankAvatar = teamRankAvatar;
	}

	/**
	 * @return the storageCapacity
	 */
	public BigDecimal getStorageCapacity() {
		return storageCapacity;
	}

	/**
	 * @param storageCapacity the storageCapacity to set
	 */
	public void setStorageCapacity(BigDecimal storageCapacity) {
		this.storageCapacity = storageCapacity;
	}

	/**
	 * @return the teamRankMemberLimit
	 */
	public int getTeamRankMemberLimit() {
		return teamRankMemberLimit;
	}

	/**
	 * @param teamRankMemberLimit the teamRankMemberLimit to set
	 */
	public void setTeamRankMemberLimit(int teamRankMemberLimit) {
		this.teamRankMemberLimit = teamRankMemberLimit;
	}

	/**
	 * @return the teamRankDescription
	 */
	public String getTeamRankDescription() {
		return teamRankDescription;
	}

	/**
	 * @param teamRankDescription the teamRankDescription to set
	 */
	public void setTeamRankDescription(String teamRankDescription) {
		this.teamRankDescription = teamRankDescription;
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