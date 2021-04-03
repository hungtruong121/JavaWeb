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
public class TeamMemberPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "team_id", nullable = false, length = 20)
	private BigInteger teamId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;

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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TeamMemberPK)) {
			return false;
		}
		TeamMemberPK castOther = (TeamMemberPK)other;
		return 
			this.teamId.equals(castOther.teamId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.teamId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
	
}