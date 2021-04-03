package com.paracelsoft.teamsport.api.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class FolderDTO {

	private BigInteger storage;
	
	private BigDecimal storageCapacity;

	private Summary media;

	private Summary photos;

	private Summary doc;

	private Summary others;
	
	private List<TeamFoldersDTO> pathFolder;

	private List<TeamFoldersDTO> listTeamFolderDTO;

	private List<TeamFilesDTO> listTeamFileDTO;

	public BigInteger getStorage() {
		return storage;
	}

	public void setStorage(BigInteger storage) {
		this.storage = storage;
	}

	public BigDecimal getStorageCapacity() {
		return storageCapacity;
	}

	public void setStorageCapacity(BigDecimal storageCapacity) {
		this.storageCapacity = storageCapacity;
	}

	public Summary getMedia() {
		return media;
	}

	public void setMedia(Summary media) {
		this.media = media;
	}

	public Summary getPhotos() {
		return photos;
	}

	public void setPhotos(Summary photos) {
		this.photos = photos;
	}

	public Summary getDoc() {
		return doc;
	}

	public void setDoc(Summary doc) {
		this.doc = doc;
	}

	public Summary getOthers() {
		return others;
	}

	public void setOthers(Summary others) {
		this.others = others;
	}

	public List<TeamFoldersDTO> getListTeamFolderDTO() {
		return listTeamFolderDTO;
	}

	public void setListTeamFolderDTO(List<TeamFoldersDTO> listTeamFolderDTO) {
		this.listTeamFolderDTO = listTeamFolderDTO;
	}

	public List<TeamFilesDTO> getListTeamFileDTO() {
		return listTeamFileDTO;
	}

	public void setListTeamFileDTO(List<TeamFilesDTO> listTeamFileDTO) {
		this.listTeamFileDTO = listTeamFileDTO;
	}

	public Summary setSummary(BigInteger totalFiles, BigInteger totalSize) {
		Summary sum = new Summary();
		sum.setTotalFiles(totalFiles);
		sum.setTotalSize(totalSize);
		return sum;

	}

	public List<TeamFoldersDTO> getPathFolder() {
		return pathFolder;
	}

	public void setPathFolder(List<TeamFoldersDTO> pathFolder) {
		this.pathFolder = pathFolder;
	}
	
	
}

class Summary {
	private BigInteger totalFiles;

	private BigInteger totalSize;

	public BigInteger getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(BigInteger totalFiles) {
		this.totalFiles = totalFiles;
	}

	public BigInteger getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(BigInteger totalSize) {
		this.totalSize = totalSize;
	}

	
}
