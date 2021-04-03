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
@Table(name="post_include_user")
@NamedQuery(name="PostIncludeUser.findAll", query="SELECT p FROM PostIncludeUser p")
public class PostIncludeUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_include_user_id", unique = true, nullable = false, length = 20)
	private BigInteger postIncludeUserId;
	
	@Column(name = "post_include_user_type", length = 20)
	private String postIncludeUseType; //tag , privacy
	
	@Column(name = "post_id", nullable = false, length = 20)
	private BigInteger postId;
	
	@Column(name = "post_comment_id", length = 20)
	private BigInteger postCommentId;
	
	@Column(name = "user_id", nullable = false, length = 20)
	private BigInteger userId;
	
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
	 * @return the postIncludeUserId
	 */
	public BigInteger getPostIncludeUserId() {
		return postIncludeUserId;
	}

	/**
	 * @param postIncludeUserId the postIncludeUserId to set
	 */
	public void setPostIncludeUserId(BigInteger postIncludeUserId) {
		this.postIncludeUserId = postIncludeUserId;
	}

	/**
	 * @return the postIncludeUseType
	 */
	public String getPostIncludeUseType() {
		return postIncludeUseType;
	}

	/**
	 * @param postIncludeUseType the postIncludeUseType to set
	 */
	public void setPostIncludeUseType(String postIncludeUseType) {
		this.postIncludeUseType = postIncludeUseType;
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
	 * @return the userId
	 */
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
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
	 * @return the postCommentId
	 */
	public BigInteger getPostCommentId() {
		return postCommentId;
	}

	/**
	 * @param postCommentId the postCommentId to set
	 */
	public void setPostCommentId(BigInteger postCommentId) {
		this.postCommentId = postCommentId;
	}

}