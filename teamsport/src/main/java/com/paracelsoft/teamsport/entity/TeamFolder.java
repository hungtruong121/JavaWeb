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
@Table(name="team_folder")
@NamedQuery(name="TeamFolder.findAll", query="SELECT p FROM TeamFolder p")
public class TeamFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "folder_id", unique = true, nullable = false, length = 20)
	private BigInteger folderId;
	
	@Column(name = "team_id", nullable = false, length = 20)
	private BigInteger teamId;
	
	@Column(name = "parent_folder_id", nullable = false, length = 20)
	private BigInteger parentFolderId;
	
	@Column(name = "folder_name")
	private String folderName;
	
	@Column(name="folder_delete_date")
	private Date folderDeleteDate;
	
	@Column(name = "event_kendo_id", nullable = false, length = 20)
	private BigInteger eventKendoId;
	
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
	
	@Column(name="folder_path_at_server")
	private String folderPathAtServer;

	/**
	 * @return the folderId
	 */
	public BigInteger getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the parentFolderId
	 */
	public BigInteger getParentFolderId() {
		return parentFolderId;
	}

	/**
	 * @param parentFolderId the parentFolderId to set
	 */
	public void setParentFolderId(BigInteger parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the folderDeleteDate
	 */
	public Date getFolderDeleteDate() {
		return folderDeleteDate;
	}

	/**
	 * @param folderDeleteDate the folderDeleteDate to set
	 */
	public void setFolderDeleteDate(Date folderDeleteDate) {
		this.folderDeleteDate = folderDeleteDate;
	}
	
	public BigInteger getEventKendoId() {
		return eventKendoId;
	}

	public void setEventKendoId(BigInteger eventKendoId) {
		this.eventKendoId = eventKendoId;
	}

	/**
	 * @return the created_by
	 */
	public BigInteger getCreatedby() {
		return createdby;
	}
	
	/**
	 * @param created_by the created_by to set
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
	 * @return the folderPathAtServer
	 */
	public String getFolderPathAtServer() {
		return folderPathAtServer;
	}

	/**
	 * @param folderPathAtServer the folderPathAtServer to set
	 */
	public void setFolderPathAtServer(String folderPathAtServer) {
		this.folderPathAtServer = folderPathAtServer;
	}
}