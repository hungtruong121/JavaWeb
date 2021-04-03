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
@Table(name="team")
@NamedQuery(name="Team.findAll", query="SELECT p FROM Team p")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id", unique = true, nullable = false, length = 20)
	private BigInteger teamId;
	
	@Column(name="team_name", nullable = false, length=200)
	private String teamName;
	
	@Column(name="team_short_name", nullable = false, length=5)
	private String teamShortName;
	
	@Column(name="team_mail", nullable = false, length=45)
	private String teamMail;
	
	@Column(name = "privacy_id", nullable = false, length = 20)
	private BigInteger privacyId; //public, myteam(private)
	
	@Column(name="team_avatar")
	private BigInteger teamAvatar = new BigInteger("1"); //uploadId
	
	@Column(name="sport_id", nullable = false, length=20)
	private BigInteger sportId;
	
	@Column(name = "team_rank_id", nullable = false, length = 20)
	private BigInteger teamRankId = new BigInteger("1");
	
	@Column(name="team_national", nullable = false)
	private String teamNational;
	
	@Column(name="team_address", nullable = false)
	private String teamAddress;
	
	@Column(name="team_slogan", length=100)
	private String teamSlogan;
	
	@Column(name="team_description", length=255)
	private String teamDescription;
	
	@Column(name="team_rank_expire")
	private Date teamRankExprie;
	
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
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the teamShortName
	 */
	public String getTeamShortName() {
		return teamShortName;
	}

	/**
	 * @param teamShortName the teamShortName to set
	 */
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}

	/**
	 * @return the teamMail
	 */
	public String getTeamMail() {
		return teamMail;
	}

	/**
	 * @param teamMail the teamMail to set
	 */
	public void setTeamMail(String teamMail) {
		this.teamMail = teamMail;
	}

	/**
	 * @return the teamAvatar
	 */
	public BigInteger getTeamAvatar() {
		return teamAvatar;
	}

	/**
	 * @param teamAvatar the teamAvatar to set
	 */
	public void setTeamAvatar(BigInteger teamAvatar) {
		this.teamAvatar = teamAvatar;
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
	 * @return the teamNational
	 */
	public String getTeamNational() {
		return teamNational;
	}

	/**
	 * @param teamNational the teamNational to set
	 */
	public void setTeamNational(String teamNational) {
		this.teamNational = teamNational;
	}

	/**
	 * @return the teamAddress
	 */
	public String getTeamAddress() {
		return teamAddress;
	}

	/**
	 * @param teamAddress the teamAddress to set
	 */
	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}

	/**
	 * @return the teamSlogan
	 */
	public String getTeamSlogan() {
		return teamSlogan;
	}

	/**
	 * @param teamSlogan the teamSlogan to set
	 */
	public void setTeamSlogan(String teamSlogan) {
		this.teamSlogan = teamSlogan;
	}

	/**
	 * @return the teamDescription
	 */
	public String getTeamDescription() {
		return teamDescription;
	}

	/**
	 * @param teamDescription the teamDescription to set
	 */
	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
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
	 * @return the privacyId
	 */
	public BigInteger getPrivacyId() {
		return privacyId;
	}

	/**
	 * @param privacyId the privacyId to set
	 */
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	/**
	 * @return the teamRankExprie
	 */
	public Date getTeamRankExprie() {
		return teamRankExprie;
	}

	/**
	 * @param teamRankExprie the teamRankExprie to set
	 */
	public void setTeamRankExprie(Date teamRankExprie) {
		this.teamRankExprie = teamRankExprie;
	}
}