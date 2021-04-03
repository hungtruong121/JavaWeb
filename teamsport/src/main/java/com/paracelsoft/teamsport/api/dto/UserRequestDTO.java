package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.List;

public class UserRequestDTO {
	
	private BigInteger userId;

	private BigInteger userAvatar;
	
	private String userFullName;
	
	private String positionSport;
	
	private BigInteger invitedBy;
	
	private String invitedByName;
	
	private List<TeamDTO> listTeamJoined;
	
	private List<TeamDTO> listTeamFollowed;
	
	private List<TeamDTO> listTeamInvited;

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

	public String getPositionSport() {
		return positionSport;
	}

	public void setPositionSport(String positionSport) {
		this.positionSport = positionSport;
	}

	public List<TeamDTO> getListTeamJoined() {
		return listTeamJoined;
	}

	public void setListTeamJoined(List<TeamDTO> listTeamJoined) {
		this.listTeamJoined = listTeamJoined;
	}

	public List<TeamDTO> getListTeamFollowed() {
		return listTeamFollowed;
	}

	public void setListTeamFollowed(List<TeamDTO> listTeamFollowed) {
		this.listTeamFollowed = listTeamFollowed;
	}

	public List<TeamDTO> getListTeamInvited() {
		return listTeamInvited;
	}

	public void setListTeamInvited(List<TeamDTO> listTeamInvited) {
		this.listTeamInvited = listTeamInvited;
	}

	/**
	 * @return the invitedBy
	 */
	public BigInteger getInvitedBy() {
		return invitedBy;
	}

	/**
	 * @param invitedBy the invitedBy to set
	 */
	public void setInvitedBy(BigInteger invitedBy) {
		this.invitedBy = invitedBy;
	}

	/**
	 * @return the invitedByName
	 */
	public String getInvitedByName() {
		return invitedByName;
	}

	/**
	 * @param invitedByName the invitedByName to set
	 */
	public void setInvitedByName(String invitedByName) {
		this.invitedByName = invitedByName;
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

}
