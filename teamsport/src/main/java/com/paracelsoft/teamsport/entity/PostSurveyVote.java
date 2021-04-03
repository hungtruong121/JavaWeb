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

@Entity
@Table(name="post_survey_vote")
@NamedQuery(name="PostSurveyVote.findAll", query="SELECT p FROM PostSurveyVote p")
public class PostSurveyVote implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "vote_id", unique = true, nullable = false, length = 20 )
	private BigInteger voteId;
	
	@Column(name = "ans_id", nullable = false, length = 20)
	private BigInteger ansId;
	
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
	 * @return the voteId
	 */
	public BigInteger getVoteId() {
		return voteId;
	}

	/**
	 * @param voteId the voteId to set
	 */
	public void setVoteId(BigInteger voteId) {
		this.voteId = voteId;
	}

	/**
	 * @return the ansId
	 */
	public BigInteger getAnsId() {
		return ansId;
	}

	/**
	 * @param ansId the ansId to set
	 */
	public void setAnsId(BigInteger ansId) {
		this.ansId = ansId;
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
