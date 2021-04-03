package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the party database table.
 * 
 */
@Entity
@Table(name="team_member")
@NamedQuery(name="TeamMember.findAll", query="SELECT p FROM TeamMember p")
public class TeamMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TeamMemberPK id;
	
//	@Column(name = "status_id", nullable = false, length = 20)
//	private BigInteger statusId;
	
	@Column(name="position_sport")
	private String positionSport;
	
	@Column(name = "team_member_role", nullable = false, length = 20)
	private String teamMemberRole;
	
	@Column(name = "team_rank_id", length = 20)
	private BigInteger teamRankId;
	
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
	 * @return the id
	 */
	public TeamMemberPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TeamMemberPK id) {
		this.id = id;
	}

	/**
	 * @return the positionSport
	 */
	public String getPositionSport() {
		return positionSport;
	}

	/**
	 * @param positionSport the positionSport to set
	 */
	public void setPositionSport(String positionSport) {
		this.positionSport = positionSport;
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
	 * @return the teamMemberRole
	 */
	public String getTeamMemberRole() {
		return teamMemberRole;
	}

	/**
	 * @param teamMemberRole the teamMemberRole to set
	 */
	public void setTeamMemberRole(String teamMemberRole) {
		this.teamMemberRole = teamMemberRole;
	}
	
}