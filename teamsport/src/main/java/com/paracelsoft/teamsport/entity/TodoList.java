package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the party database table.
 * 
 */
@Entity
@Table(name="todo_list")
@NamedQuery(name="TodoList.findAll", query="SELECT t FROM TodoList t")
public class TodoList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "todo_list_id", unique = true, nullable = false, length = 20)
	private BigInteger todoListId;
	
	@Column(name = "team_id", length = 20)
	private BigInteger teamId;
	
	@Column(name = "todo_list_parent_id", length = 20)
	private BigInteger todoListParentId;
	
	@Column(name="todo_list_title", length=100)
	private String todoListTitle;
	
	@Column(name="todo_list_type", length=45)
	private String todoLisType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="todo_list_deadline")
	private Date todoListDeadline;
	
	@Column(name="todo_list_notice")
	private String todoListNotice; 
	
	@Column(name = "privacy_id", length = 20)
	private BigInteger privacyId;
	
	@Column(name="created_by")
	private BigInteger createdby;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the todoListId
	 */
	public BigInteger getTodoListId() {
		return todoListId;
	}

	/**
	 * @param todoListId the todoListId to set
	 */
	public void setTodoListId(BigInteger todoListId) {
		this.todoListId = todoListId;
	}

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
	 * @return the todoListParentId
	 */
	public BigInteger getTodoListParentId() {
		return todoListParentId;
	}

	/**
	 * @param todoListParentId the todoListParentId to set
	 */
	public void setTodoListParentId(BigInteger todoListParentId) {
		this.todoListParentId = todoListParentId;
	}

	/**
	 * @return the todoListTitle
	 */
	public String getTodoListTitle() {
		return todoListTitle;
	}

	/**
	 * @param todoListTitle the todoListTitle to set
	 */
	public void setTodoListTitle(String todoListTitle) {
		this.todoListTitle = todoListTitle;
	}

	/**
	 * @return the todoLisType
	 */
	public String getTodoLisType() {
		return todoLisType;
	}

	/**
	 * @param todoLisType the todoLisType to set
	 */
	public void setTodoLisType(String todoLisType) {
		this.todoLisType = todoLisType;
	}

	/**
	 * @return the todoListDeadline
	 */
	public Date getTodoListDeadline() {
		return todoListDeadline;
	}

	/**
	 * @param todoListDeadline the todoListDeadline to set
	 */
	public void setTodoListDeadline(Date todoListDeadline) {
		this.todoListDeadline = todoListDeadline;
	}

	/**
	 * @return the todoListNotice
	 */
	public String getTodoListNotice() {
		return todoListNotice;
	}

	/**
	 * @param todoListNotice the todoListNotice to set
	 */
	public void setTodoListNotice(String todoListNotice) {
		this.todoListNotice = todoListNotice;
	}

	/**
	 * @return the privacyId
	 */
	public BigInteger getPrivacyId() {
		return privacyId;
	}

	/**
	 * @param privacyId the privacyId to set
	 */
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	/**
	 * @return the createdby
	 */
	public BigInteger getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
