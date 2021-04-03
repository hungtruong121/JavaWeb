package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class TeamFollowDTO {

	private List<TeamFollow> lisTeamFollow;
	private int teamUserFollowing;
	
	public List<TeamFollow> getLisTeamFollow() {
		return lisTeamFollow;
	}
	public void setLisTeamFollow(List<TeamFollow> lisTeamFollow) {
		this.lisTeamFollow = lisTeamFollow;
	}
	public int getTeamUserFollowing() {
		return teamUserFollowing;
	}
	public void setTeamUserFollowing(int teamUserFollowing) {
		this.teamUserFollowing = teamUserFollowing;
	}
	
}
