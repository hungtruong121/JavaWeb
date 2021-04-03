package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchTeamDTO {

	private List<TeamDTO> teams;
	
	private Integer total;

	/**
	 * @return the teams
	 */
	public List<TeamDTO> getTeams() {
		return teams;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<TeamDTO> teams) {
		this.teams = teams;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}
