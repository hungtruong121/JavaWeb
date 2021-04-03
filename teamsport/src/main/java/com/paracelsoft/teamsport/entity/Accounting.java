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
@Table(name="accounting")
@NamedQuery(name="Accounting.findAll", query="SELECT a FROM Accounting a")
public class Accounting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accounting_id", unique = true, nullable = false, length = 20)
	private BigInteger accountingId;
	
	@Column(name = "team_id", length = 20)
	private BigInteger teamId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="accounting_deadline")
	private Date accountingDeadline;
	
	@Column(name="accounting_title", length=100)
	private String accountingTitle;
	
	@Column(name="status_id", length = 20)
	private BigInteger statusId; 
	
	@Column(name="accounting_amount", length=50)
	private String accountingAmount; 
	
	@Column(name="accounting_notice")
	private String accountingNotice;
	
	@Column(name="accounting_evidence", length = 20)
	private BigInteger accountingEvidence; 
	
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
	 * @return the accountingId
	 */
	public BigInteger getAccountingId() {
		return accountingId;
	}

	/**
	 * @param accountingId the accountingId to set
	 */
	public void setAccountingId(BigInteger accountingId) {
		this.accountingId = accountingId;
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
	 * @return the accountingDeadline
	 */
	public Date getAccountingDeadline() {
		return accountingDeadline;
	}

	/**
	 * @param accountingDeadline the accountingDeadline to set
	 */
	public void setAccountingDeadline(Date accountingDeadline) {
		this.accountingDeadline = accountingDeadline;
	}

	/**
	 * @return the accountingTitle
	 */
	public String getAccountingTitle() {
		return accountingTitle;
	}

	/**
	 * @param accountingTitle the accountingTitle to set
	 */
	public void setAccountingTitle(String accountingTitle) {
		this.accountingTitle = accountingTitle;
	}

	/**
	 * @return the statusId
	 */
	public BigInteger getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the accountingAmount
	 */
	public String getAccountingAmount() {
		return accountingAmount;
	}

	/**
	 * @param accountingAmount the accountingAmount to set
	 */
	public void setAccountingAmount(String accountingAmount) {
		this.accountingAmount = accountingAmount;
	}

	/**
	 * @return the accountingNotice
	 */
	public String getAccountingNotice() {
		return accountingNotice;
	}

	/**
	 * @param accountingNotice the accountingNotice to set
	 */
	public void setAccountingNotice(String accountingNotice) {
		this.accountingNotice = accountingNotice;
	}

	/**
	 * @return the accountingEvidence
	 */
	public BigInteger getAccountingEvidence() {
		return accountingEvidence;
	}

	/**
	 * @param accountingEvidence the accountingEvidence to set
	 */
	public void setAccountingEvidence(BigInteger accountingEvidence) {
		this.accountingEvidence = accountingEvidence;
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
