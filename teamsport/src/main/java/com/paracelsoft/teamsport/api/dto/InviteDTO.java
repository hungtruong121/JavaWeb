package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.List;

public class InviteDTO {

	private BigInteger teamId;

	private List<String> emailInvite;

	private BigInteger userId;

	private BigInteger userAvatar;

	private String userFullName;

	private String teamJoined;

	private String sportName;

	private String status;

	public List<String> getEmailInvite() {
		return emailInvite;
	}

	public void setEmailInvite(List<String> emailInvite) {
		this.emailInvite = emailInvite;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(BigInteger userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeamJoined() {
		return teamJoined;
	}

	public void setTeamJoined(String teamJoined) {
		this.teamJoined = teamJoined;
	}

}