package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.paracelsoft.teamsport.entity.TodoList;

public class TodoListDTO {
	private BigInteger todoListId;
	
	private BigInteger teamId;
	
	private BigInteger userId;
	
	private String timeZone;
	
	private BigInteger privacyId; // 2:MY_TEAM || 3:INCLUDE || 4:ONLY_ME
	
	private List<BigInteger> userAssign;
	
	private BigInteger todoListParentId;
	
	private String todoListTitle;
	
	private String todoListType; //ConstantUtils: SINGLE || GROUP
	
	private String todoListDeadline;
	
	private Date dateDeadline;
	
	private String todoListNotice;
	
	private int totalMemberAssign;
	
	private int totalMemberDone;
	
	private BigInteger taskStatus;
	
	private boolean isExpired;
	
	private TodoList parentTask;
	
	private int generalProgress; // number member hoàn thành task/tổng số lượng member được assign task
	
	private List<UserDTO> listUserAssigned;
	
	private UserDTO createdBy;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private List<TodoListDTO> todoListChild;

	public BigInteger getTodoListId() {
		return todoListId;
	}

	public void setTodoListId(BigInteger todoListId) {
		this.todoListId = todoListId;
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

	public List<BigInteger> getUserAssign() {
		return userAssign;
	}

	public void setUserAssign(List<BigInteger> userAssign) {
		this.userAssign = userAssign;
	}

	public BigInteger getTodoListParentId() {
		return todoListParentId;
	}

	public void setTodoListParentId(BigInteger todoListParentId) {
		this.todoListParentId = todoListParentId;
	}

	public String getTodoListTitle() {
		return todoListTitle;
	}

	public void setTodoListTitle(String todoListTitle) {
		this.todoListTitle = todoListTitle;
	}

	public String getTodoListType() {
		return todoListType;
	}

	public void setTodoLisType(String todoListType) {
		this.todoListType = todoListType;
	}

	public String getTodoListDeadline() {
		return todoListDeadline;
	}

	public void setTodoListDeadline(String todoListDeadline) {
		this.todoListDeadline = todoListDeadline;
	}

	public Date getDateDeadline() {
		return dateDeadline;
	}

	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}

	public String getTodoListNotice() {
		return todoListNotice;
	}

	public void setTodoListNotice(String todoListNotice) {
		this.todoListNotice = todoListNotice;
	}

	public UserDTO getCreatedby() {
		return createdBy;
	}

	public void setCreatedby(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<TodoListDTO> getTodoListChild() {
		return todoListChild;
	}

	public void setTodoListChild(List<TodoListDTO> todoListChild) {
		this.todoListChild = todoListChild;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public BigInteger getPrivacyId() {
		return privacyId;
	}

	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	public int getGeneralProgress() {
		return generalProgress;
	}

	public void setGeneralProgress(int generalProgress) {
		this.generalProgress = generalProgress;
	}

	public List<UserDTO> getListUserAssigned() {
		return listUserAssigned;
	}

	public void setListUserAssigned(List<UserDTO> listUserAssigned) {
		this.listUserAssigned = listUserAssigned;
	}

	public TodoList getParentTask() {
		return parentTask;
	}

	public void setParentTask(TodoList parentTask) {
		this.parentTask = parentTask;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public BigInteger getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(BigInteger taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int getTotalMemberAssign() {
		return totalMemberAssign;
	}

	public void setTotalMemberAssign(int totalMemberAssign) {
		this.totalMemberAssign = totalMemberAssign;
	}

	public int getTotalMemberDone() {
		return totalMemberDone;
	}

	public void setTotalMemberDone(int totalMemberDone) {
		this.totalMemberDone = totalMemberDone;
	}
}
