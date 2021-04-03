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
 */
@Entity
@Table(name="post_survey_ans")
@NamedQuery(name="PostSurveyAns.findAll", query="SELECT p FROM PostSurveyAns p")
public class PostSurveyAns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ans_id", unique = true, nullable = false, length = 20)
	private BigInteger ansId;
	
	@Column(name = "post_id", length = 20)
	private BigInteger postId;
	
	@Column(name="ans_content")
	private String ansContent;
	
	@Column(name="ans_image")
	private BigInteger ansImage;
	
	@Column(name="background_color")
	private String backgroundColor;
	
	@Column(name="post_content_font_size")
	private String postContentFontSize;
	
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
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}

	/**
	 * @return the ansContent
	 */
	public String getAnsContent() {
		return ansContent;
	}

	/**
	 * @param ansContent the ansContent to set
	 */
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}

	/**
	 * @return the ansImage
	 */
	public BigInteger getAnsImage() {
		return ansImage;
	}

	/**
	 * @param ansImage the ansImage to set
	 */
	public void setAnsImage(BigInteger ansImage) {
		this.ansImage = ansImage;
	}

	/**
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the postContentFontSize
	 */
	public String getPostContentFontSize() {
		return postContentFontSize;
	}

	/**
	 * @param postContentFontSize the postContentFontSize to set
	 */
	public void setPostContentFontSize(String postContentFontSize) {
		this.postContentFontSize = postContentFontSize;
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
