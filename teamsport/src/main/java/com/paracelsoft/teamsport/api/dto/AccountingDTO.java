package com.paracelsoft.teamsport.api.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class AccountingDTO {

	private BigInteger accountingId;
	
	private BigInteger teamId;
	
	private BigInteger userId;
	
	private List<BigInteger> userInclude;
	
	private String timeZone;
	
	private String title;
	
	private String amount;
	
	private String deadline;
	
	private Date dateDeadline;
	
	private String notice;
	
	private Boolean isExpired;
	
	private BigInteger statusPaid;
	
	private BigInteger imgEvidence;
	
	private int totalMemberPaid;
	
	private int totalMemberNotPaid;
	
	private int totalMemberWaiting;
	
	private int totalMemberDone;
	
	private int totalMemberInclude;
	
	private List<UserDTO> listMemberNotPaid;
	
	private List<UserDTO> listMemberWaiting;
	
	private List<UserDTO> listMemberDone;
	
	private BigInteger createdby;
	
	private int isActive = 1;
	
	private Date createdDate;
	
	private Date updatedDate;

	public BigInteger getAccountingId() {
		return accountingId;
	}

	public void setAccountingId(BigInteger accountingId) {
		this.accountingId = accountingId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
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

	public List<BigInteger> getUserInclude() {
		return userInclude;
	}

	public void setUserInclude(List<BigInteger> userInclude) {
		this.userInclude = userInclude;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public int getTotalMemberPaid() {
		return totalMemberPaid;
	}

	public void setTotalMemberPaid(int totalMemberPaid) {
		this.totalMemberPaid = totalMemberPaid;
	}

	public int getTotalMemberNotPaid() {
		return totalMemberNotPaid;
	}

	public void setTotalMemberNotPaid(int totalMemberNotPaid) {
		this.totalMemberNotPaid = totalMemberNotPaid;
	}

	public int getTotalMemberInclude() {
		return totalMemberInclude;
	}

	public void setTotalMemberInclude(int totalMemberInclude) {
		this.totalMemberInclude = totalMemberInclude;
	}

	public Date getDateDeadline() {
		return dateDeadline;
	}

	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}

	public int getTotalMemberWaiting() {
		return totalMemberWaiting;
	}

	public void setTotalMemberWaiting(int totalMemberWaiting) {
		this.totalMemberWaiting = totalMemberWaiting;
	}

	public int getTotalMemberDone() {
		return totalMemberDone;
	}

	public void setTotalMemberDone(int totalMemberDone) {
		this.totalMemberDone = totalMemberDone;
	}

	public List<UserDTO> getListMemberNotPaid() {
		return listMemberNotPaid;
	}

	public void setListMemberNotPaid(List<UserDTO> listMemberNotPaid) {
		this.listMemberNotPaid = listMemberNotPaid;
	}

	public List<UserDTO> getListMemberWaiting() {
		return listMemberWaiting;
	}

	public void setListMemberWaiting(List<UserDTO> listMemberWaiting) {
		this.listMemberWaiting = listMemberWaiting;
	}

	public List<UserDTO> getListMemberDone() {
		return listMemberDone;
	}

	public void setListMemberDone(List<UserDTO> listMemberDone) {
		this.listMemberDone = listMemberDone;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getStatusPaid() {
		return statusPaid;
	}

	public void setStatusPaid(BigInteger statusPaid) {
		this.statusPaid = statusPaid;
	}

	public BigInteger getImgEvidence() {
		return imgEvidence;
	}

	public void setImgEvidence(BigInteger imgEvidence) {
		this.imgEvidence = imgEvidence;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
}
