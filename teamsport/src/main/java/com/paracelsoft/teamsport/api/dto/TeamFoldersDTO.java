package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class TeamFoldersDTO {

	private BigInteger folderId;
	
	private BigInteger teamId;

	private BigInteger parentFolderId;

	private String folderName;

	private Date createdDate;

	private Date updatedDate;

	private Date folderDeleteDate;

	private BigInteger folderSize;

	private int isActive = 1;

	private BigInteger createdby;
	
	private String createdByName;
	
	private List<TeamFoldersDTO> folderChild;

	public BigInteger getFolderId() {
		return folderId;
	}

	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
	}

	public BigInteger getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(BigInteger parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
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

	public Date getFolderDeleteDate() {
		return folderDeleteDate;
	}

	public void setFolderDeleteDate(Date folderDeleteDate) {
		this.folderDeleteDate = folderDeleteDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigInteger getFolderSize() {
		return folderSize;
	}

	public void setFolderSize(BigInteger folderSize) {
		this.folderSize = folderSize;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public List<TeamFoldersDTO> getFolderChild() {
		return folderChild;
	}

	public void setFolderChild(List<TeamFoldersDTO> folderChild) {
		this.folderChild = folderChild;
	}
}
