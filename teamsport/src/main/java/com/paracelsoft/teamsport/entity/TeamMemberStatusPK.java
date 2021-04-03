package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The persistent class for the party database table.
 * 
 */
@Embeddable
public class TeamMemberStatusPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "team_id", nullable = false, length = 20)
	private BigInteger teamId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;
	
	@Column(name = "status_id", nullable = false, length = 20)
	private BigInteger statusId;

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
	 * @return the userId
	 */
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	/**
	 * @return the statusId
	 */
	public BigInteger getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TeamMemberStatusPK)) {
			return false;
		}
		TeamMemberStatusPK castOther = (TeamMemberStatusPK)other;
		return 
			this.teamId.equals(castOther.teamId)
			&& this.userId.equals(castOther.userId)
			&& this.statusId.equals(castOther.teamId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.teamId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.statusId.hashCode();
		
		return hash;
	}
	
}