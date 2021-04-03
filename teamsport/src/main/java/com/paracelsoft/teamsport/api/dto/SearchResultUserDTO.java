package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchResultUserDTO {
	
	private List<UserDTO> users;
	private Integer total;
	
	/**
	 * @return the users
	 */
	public List<UserDTO> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
