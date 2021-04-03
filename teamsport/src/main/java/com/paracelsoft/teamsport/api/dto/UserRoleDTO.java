package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class UserRoleDTO {
	
	List<UserDTO> teamAdmin;
	
	List<UserDTO> teamMember;
	
	long total; //total admin and member

	/**
	 * @return the teamAdmin
	 */
	public List<UserDTO> getTeamAdmin() {
		return teamAdmin;
	}

	/**
	 * @param teamAdmin the teamAdmin to set
	 */
	public void setTeamAdmin(List<UserDTO> teamAdmin) {
		this.teamAdmin = teamAdmin;
	}

	/**
	 * @return the teamMember
	 */
	public List<UserDTO> getTeamMember() {
		return teamMember;
	}

	/**
	 * @param teamMember the teamMember to set
	 */
	public void setTeamMember(List<UserDTO> teamMember) {
		this.teamMember = teamMember;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	
}
