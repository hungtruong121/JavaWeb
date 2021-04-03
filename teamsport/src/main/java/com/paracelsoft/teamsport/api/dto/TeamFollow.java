package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;

public class TeamFollow {

	private BigInteger userId;
	private BigInteger teamId;
	private BigInteger teamAvatar = new BigInteger("1");
	private String teamName;
	private String teamShortName;
	private int membersInTeam;
	private int followersInTeam;
	
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getTeamId() {
		return teamId;
	}
	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
	public int getMembersInTeam() {
		return membersInTeam;
	}
	public void setMembersInTeam(int membersInTeam) {
		this.membersInTeam = membersInTeam;
	}
	public int getFollowersInTeam() {
		return followersInTeam;
	}
	public void setFollowersInTeam(int followersInTeam) {
		this.followersInTeam = followersInTeam;
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
	
}
