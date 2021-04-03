package com.paracelsoft.teamsport.api.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class UploadDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigInteger uploadId;
	private String fileName;
	private String originalName;
	private String filePath;
	private String contentType;
	private String fileNameAtServer;
	
	public BigInteger getUploadId() {
		return uploadId;
	}
	public void setUploadId(BigInteger uploadId) {
		this.uploadId = uploadId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
}
