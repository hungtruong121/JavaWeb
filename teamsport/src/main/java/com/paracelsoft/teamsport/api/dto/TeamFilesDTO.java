package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;

public class TeamFilesDTO {

	private BigInteger fileId;
	
	private BigInteger folderId;
	
	private BigInteger teamId;
	
	private String fileType;
		
	private String fileName;
		
	private String contentType;
	
	private String fileNameAtServer;
	
	private String filePathAtServer;
	
	private BigInteger fileSize;
	
	private Date fileDeleteDate;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private int isActive = 1;
	
	private BigInteger createdby;
	
	private String createdByName;

	public BigInteger getFileId() {
		return fileId;
	}

	public void setFileId(BigInteger fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public BigInteger getFolderId() {
		return folderId;
	}

	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileNameAtServer() {
		return fileNameAtServer;
	}

	public void setFileNameAtServer(String fileNameAtServer) {
		this.fileNameAtServer = fileNameAtServer;
	}

	public String getFilePathAtServer() {
		return filePathAtServer;
	}

	public void setFilePathAtServer(String filePathAtServer) {
		this.filePathAtServer = filePathAtServer;
	}

	public BigInteger getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	public Date getFileDeleteDate() {
		return fileDeleteDate;
	}

	public void setFileDeleteDate(Date fileDeleteDate) {
		this.fileDeleteDate = fileDeleteDate;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}	
}
