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
@Table(name="team_file")
@NamedQuery(name="TeamFile.findAll", query="SELECT p FROM TeamFile p")
public class TeamFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id", unique = true, nullable = false, length = 20)
	private BigInteger fileId;
	
	@Column(name = "team_id", length = 20)
	private BigInteger teamId;
	
	@Column(name = "folder_id", length = 20)
	private BigInteger folderId;
	
	@Column(name = "file_type", length = 255)
	private String fileType;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name="file_content_type")
	private String fileContentType;
	
	@Column(name="file_name_at_server")
	private String fileNameAtServer;
	
	@Column(name="file_path_at_server")
	private String filePathAtServer;
	
	@Column(name="file_size")
	private BigInteger fileSize;
	
	@Column(name="file_delete_date")
	private Date fileDeleteDate;
	
	@Column(name="created_by")
	private BigInteger createdBy;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the file_id
	 */
	public BigInteger getFileId() {
		return fileId;
	}

	/**
	 * @param file_id the file_id to set
	 */
	public void setFileId(BigInteger fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the fileNameAtServer
	 */
	public String getFileNameAtServer() {
		return fileNameAtServer;
	}

	/**
	 * @param fileNameAtServer the fileNameAtServer to set
	 */
	public void setFileNameAtServer(String fileNameAtServer) {
		this.fileNameAtServer = fileNameAtServer;
	}

	/**
	 * @return the filePathAtServer
	 */
	public String getFilePathAtServer() {
		return filePathAtServer;
	}

	/**
	 * @param filePathAtServer the filePathAtServer to set
	 */
	public void setFilePathAtServer(String filePathAtServer) {
		this.filePathAtServer = filePathAtServer;
	}

	/**
	 * @return the fileSize
	 */
	public BigInteger getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the fileDeleteDate
	 */
	public Date getFileDeleteDate() {
		return fileDeleteDate;
	}

	/**
	 * @param fileDeleteDate the fileDeleteDate to set
	 */
	public void setFileDeleteDate(Date fileDeleteDate) {
		this.fileDeleteDate = fileDeleteDate;
	}

	/**
	 * @return the created_by
	 */
	public BigInteger getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param created_by the created_by to set
	 */
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
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
	
}