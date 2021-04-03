package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.List;

public class SearchDTO {
	
	private BigInteger teamId;
	
	private String keyword;
	
	private String fromDate;
	
	private String toDate;
	
	private BigInteger privacyId;
	
	private String searchType;
	
	private int firstResult = 0;
	
	private int maxResult = 10;
	
	private String sort;
	
	private String sortAction; //desc,asc
	
	List<BigInteger> listUserIds;
	
	List<TeamFoldersDTO> listTeamFolderDTO;
	
	List<TeamFilesDTO> listTeamFileDTO;
	
	List<TeamDTO> listTeamDTO;
	
	List<UserDTO> listUserDTO;
	
	List<PostDTO> listPostDTO;

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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @return the privacyId
	 */
	public BigInteger getPrivacyId() {
		return privacyId;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param privacyId the privacyId to set
	 */
	public void setPrivacyId(BigInteger privacyId) {
		this.privacyId = privacyId;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the maxResult
	 */
	public int getMaxResult() {
		return maxResult;
	}

	/**
	 * @param maxResult the maxResult to set
	 */
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the sortAction
	 */
	public String getSortAction() {
		return sortAction;
	}

	/**
	 * @param sortAction the sortAction to set
	 */
	public void setSortAction(String sortAction) {
		this.sortAction = sortAction;
	}

	/**
	 * @return the listUserIds
	 */
	public List<BigInteger> getListUserIds() {
		return listUserIds;
	}

	/**
	 * @param listUserIds the listUserIds to set
	 */
	public void setListUserIds(List<BigInteger> listUserIds) {
		this.listUserIds = listUserIds;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public List<TeamDTO> getListTeamDTO() {
		return listTeamDTO;
	}

	public void setListTeamDTO(List<TeamDTO> listTeamDTO) {
		this.listTeamDTO = listTeamDTO;
	}

	public List<UserDTO> getListUserDTO() {
		return listUserDTO;
	}

	public void setListUserDTO(List<UserDTO> listUserDTO) {
		this.listUserDTO = listUserDTO;
	}

	public List<PostDTO> getListPostDTO() {
		return listPostDTO;
	}

	public void setListPostDTO(List<PostDTO> listPostDTO) {
		this.listPostDTO = listPostDTO;
	}
}
