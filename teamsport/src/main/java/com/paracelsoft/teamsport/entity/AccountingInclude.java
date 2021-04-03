package com.paracelsoft.teamsport.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the party database table.
 * 
 */
@Entity
@Table(name="accounting_include")
@NamedQuery(name="AccountingInclude.findAll", query="SELECT a FROM AccountingInclude a")
public class AccountingInclude implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccountingIncludePK id;
	
	@Column(name="status_id", length = 20)
	private BigInteger statusId; 
	
	@Column(name="img_evidence", length = 20)
	private BigInteger imgEvidence;
	
	@Column(name="created_by")
	private BigInteger createdby;
	
	@Column(name="confirm_by")
	private BigInteger confirmBy;
	
	@Column(name="is_active")
	private int isActive = 1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	/**
	 * @return the id
	 */
	public AccountingIncludePK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(AccountingIncludePK id) {
		this.id = id;
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
	 * @return the imgEvidence
	 */
	public BigInteger getImgEvidence() {
		return imgEvidence;
	}

	/**
	 * @param imgEvidence the imgEvidence to set
	 */
	public void setImgEvidence(BigInteger imgEvidence) {
		this.imgEvidence = imgEvidence;
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

	/**
	 * @return the confirmBy
	 */
	public BigInteger getConfirmBy() {
		return confirmBy;
	}

	/**
	 * @param confirmBy the confirmBy to set
	 */
	public void setConfirmBy(BigInteger confirmBy) {
		this.confirmBy = confirmBy;
	}
	
}
