package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchTodoListDTO {

	private List<TodoListDTO> listTasks;
	
	private Integer totalNotDone;
	
	private Integer total;

	/**
	 * @return the listTasks
	 */
	public List<TodoListDTO> getListTasks() {
		return listTasks;
	}

	/**
	 * @param listTasks the listTasks to set
	 */
	public void setListTasks(List<TodoListDTO> listTasks) {
		this.listTasks = listTasks;
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

	public Integer getTotalNotDone() {
		return totalNotDone;
	}

	public void setTotalNotDone(Integer totalNotDone) {
		this.totalNotDone = totalNotDone;
	}

}
